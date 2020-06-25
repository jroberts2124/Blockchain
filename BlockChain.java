import java.util.ArrayList;
import java.util.HashMap;

/* Block Chain should maintain only limited block nodes to satisfy the functions
   You should not have the all the blocks added to the block chain in memory 
   as it would overflow memory
 */

public class BlockChain {
   public static final int CUT_OFF_AGE = 10;

   // all information required in handling a block in block chain
   private class BlockNode {
      public Block b;
      public BlockNode parent;
      public ArrayList<BlockNode> children;
      public int height;
      // utxo pool for making a new block on top of this block
      private UTXOPool uPool;
      public HashMap<byte[], byte[]> blockChain;

      public BlockNode(Block b, BlockNode parent, UTXOPool uPool) {
         this.b = b;
         this.parent = parent;
         children = new ArrayList<BlockNode>();
         this.uPool = uPool;
         if (parent != null) {
            height = parent.height + 1;
            parent.children.add(this);
         } else {
            height = 1;
         }
      }

      public UTXOPool getUTXOPoolCopy() {
         return new UTXOPool(uPool);
      }
   }

   /* create an empty block chain with just a genesis block.
    * Assume genesis block is a valid block
    */
   public BlockChain(Block genesisBlock) {
      // IMPLEMENT THIS
      blockchain = new HashMap<byte[], byte[]>();
      UTXOPool utxoPool = new UTXOPool(); // list fo transactions

      blockChain = new HashMap<>(); // new blockchain is created 
      

      addCoinbaseToUTXOPool(genesisBlock, utxoPool); // adds to the genesis block

      BlockNode genesisNode = new BlockNode(genesisBlock, null, utxoPool);// the genesis node is changed


      blockChain.put(wrap(genesisBlock.getHash()), genesisNode); // combines hash with node
      //wrap is used to modify the the blockchain
      txPool = new TransactionPool(); // new transaction for transaction pool
      
      maxHeightNode = genesisNode; // genesisNode has the maximum height changed
   }

   /* Get the maximum height block
    */
   public Block getMaxHeightBlock() {
      // IMPLEMENT THIS
      return maxHeightNode.b; //return max node by calling out maxheight of the current node b
      
   }
   
   /* Get the UTXOPool for mining a new block on top of 
    * max height block
    */
   public UTXOPool getMaxHeightUTXOPool() {
      // IMPLEMENT THIS
      return maxHeightNode.getUTXOPoolCopy(); // return latest transaction from pool
   }
   
   /* Get the transaction pool to mine a new block
    */
   public TransactionPool getTransactionPool() {
      // IMPLEMENT THIS
      return txPool; // return transactions
   }

   /* Add a block to block chain if it is valid.
    * For validity, all transactions should be valid
    * and block should be at height > (maxHeight - CUT_OFF_AGE).
    * For example, you can try creating a new block over genesis block 
    * (block height 2) if blockChain height is <= CUT_OFF_AGE + 1. 
    * As soon as height > CUT_OFF_AGE + 1, you cannot create a new block at height 2.
    * Return true of block is successfully added
    */
   public boolean addBlock(Block b) {
       // IMPLEMENT THIS
       byte[] prevBlockHash = block.getPrevBlockHash();
       
       if (prevBlockHash == null) // if list is empty
            return false; // return nothing
        BlockNode parentBlockNode = blockChain.get(wrap(prevBlockHash));
        if (parentBlockNode != null) { // if previous node isn't null
            return true; // return true
        }
        else{
           return false; 
        }
        TxHandler handler = new TxHandler(parentBlockNode.getUTXOPoolCopy()); // new list that copies tranascitons

        Transaction[] txs = block.getTransactions().toArray(new Transaction[0]); // adds new transaction to array

        Transaction[] validTxs = handler.handleTxs(txs); // new transaction

        if (validTxs.length == txs.length) { // if length of tranasction is the same
            return true; // return true
        }
        else{
           return false; //if transaction is either too short or long
        }
        int proposedHeight = parentBlockNode.height + 1; // only up to 2 transactions
        if (proposedHeight <= maxHeightNode.height - CUT_OFF_AGE) { // 
            return false;
        }
        UTXOPool utxoPool = handler.getUTXOPool();

        addCoinbaseToUTXOPool(block, utxoPool); // add coin to block

        BlockNode node = new BlockNode(block, parentBlockNode, utxoPool); // create ne node

        blockChain.put(wrap(block.getHash()), node); // combine hash to node
        if (proposedHeight > maxHeightNode.height) { // make new node the new addition
            maxHeightNode = node;
        }
        return true;
   }

   /* Add a transaction in transaction pool
    */
   public void addTransaction(Transaction tx) {
      // IMPLEMENT THIS
      txpool.addTransaction(tx);
   }
