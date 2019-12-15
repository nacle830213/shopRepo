package main

import (
	"blockchains/RepoBlockChain/bolt"
	"fmt"
	"log"
	"os"
)

const  blockBucket  = "blockBucket"
const  lastHashKey  =  "lastHashKey"
type BlockChain struct {
	db *bolt.DB   //数据库句柄
	tail []byte	  //最后一个区块的hash
}

func CreateBlockChain()*BlockChain  {
	//1.获得数据库句柄  打开数据库  读写数据
	db ,err :=bolt.Open("blockchain.db",0600,nil )
	if err!=nil {
		log.Panic(err)
	}
	//defer db.Close()
	var tail []byte

	db.Update(func(tx *bolt.Tx) error {
		//为空说明不存在就创建桶
		//判断是否有bucket  如果有没有创建
		//写入创世块
		//写入lashHashKey
		b := tx.Bucket([]byte(blockBucket))
		if b==nil {//为空说明不存在就创建桶
			fmt.Printf("创建区块链\n")
			b ,err :=tx.CreateBucket([]byte(blockBucket))
			if err!=nil{
				log.Panic(err)
			}
			//创建好bucket 添加创世区块 ,创世块中只有挖矿交易
			genesisBlock := NewBlock([]*Record{},[]byte{})
			b.Put(genesisBlock.Hash,genesisBlock.Serialize())
			blockInfo :=b.Get(genesisBlock.Hash)
			block:= DeSerialize(blockInfo)
			fmt.Printf("block %v\n",block)
			//添加最后一个block
			b.Put([]byte(lastHashKey),genesisBlock.Hash)
			tail=genesisBlock.Hash//更新tail 为最后一个区块的哈希 //返回bc实例
		}else {
			tail=b.Get([]byte(lastHashKey))
		}
		return nil
	})

	return &BlockChain{db:db,tail:tail}
}
func (bc *BlockChain)AddBlock(records []*Record)  {
	db :=bc.db
	db.Update(func(tx *bolt.Tx) error {
		b := tx.Bucket([]byte(blockBucket))
		if b==nil {//为空说明不存在请检查
			fmt.Printf("bucket 不存在请检查\n")
			os.Exit(1)
		}else {	//更新tail 为最后一个区块的哈希
			//返回bc实例
			block :=NewBlock(records,bc.tail)
			b.Put(block.Hash,block.Serialize())
			b.Put([]byte(lastHashKey),block.Hash)
			bc.tail = block.Hash
		}
		return nil
	})
}
type BlockChainIterator struct {
	db  *bolt.DB
	current []byte
}
//生成迭代器
func (bc *BlockChain)NewIterator()*BlockChainIterator  {
	return &BlockChainIterator{db:bc.db,current:bc.tail}
}
func (it *BlockChainIterator)Next()*Block{
	var block Block
	it.db.View(func(tx *bolt.Tx) error {
		b := tx.Bucket([]byte(blockBucket))
		if b==nil {//为空说明不存在请检查
			fmt.Printf("bucket 不存在请检查\n")
			os.Exit(1)
		}
		blockInfo :=b.Get(it.current) //block 字节流
		block =*DeSerialize(blockInfo) //解码
		it.current = block.PreBlockHash
		return  nil
	})
	return &block
}