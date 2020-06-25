# Blockchain
In this assignment you will implement a node that’s part of a block-chain-based distributed consensus protocol. Specifically, your code will receive incoming transactions and blocks and maintain an updated block chain.

In this assignment you will implement a node that’s part of a block-chain-based distributed
consensus protocol. Specifically, your code will receive incoming transactions and blocks and
maintain an updated block chain.
Files provided:
Block.java Stores the block data structure.
BlockHandler.java Uses BlockChain.java to process a newly received block, create a
new block, or process a newly received transaction.
ByteArrayWrapper.java A utility file which creates a wrapper for byte arrays such that it
could be used as a key in hash functions. (See
TransactionPool.java)
Transaction.java This is similar to Transaction.java as provided in Assignment 1
except for introducing functionality to create a coinbase
transaction. Take a look at Block.java constructor to see how a
coinbase transaction is created.
TransactionPool.java Implements a pool of transactions, required when creating a new
block.
UTXO.java From Assignment 1.
UTXOPool.java From Assignment 1.
TxHandler.java From Assignment 1 with some changes.
File to be modified:
BlockChain.java
The BlockChain class is responsible for maintaining a block chain. Since the entire block chain 
could be huge in size, you should only keep around the most recent blocks. The exact number
to store is up to your design, as long as you’re able to implement all the API functions.
Since there can be (multiple) forks, blocks form a tree rather than a list. Your design should take
this into account. You have to maintain a UTXO pool corresponding to every block on top of
which a new block might be created.
