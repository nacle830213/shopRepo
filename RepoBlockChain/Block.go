package main

import (
	"bytes"
	"crypto/sha256"
	"encoding/binary"
	"encoding/gob"
	"log"
	"time"
)

type Block struct {
	//区块头
	PreBlockHash [] byte//前哈希
	MerKleRoot []byte
	TimeStamp uint64//时间戳

	Hash [] byte
	//区块体
	Records []*Record

}

func NewBlock(records []*Record,preblockhash []byte) *Block  {
	block := Block{
		PreBlockHash: preblockhash,
		MerKleRoot:   []byte{},
		TimeStamp:    uint64(time.Now().Unix()),
		Hash:         nil,
		Records:      records,
	}
	temp :=[][]byte{
		block.PreBlockHash,
		block.MerKleRoot,
		uintToByte(block.TimeStamp),
	}
	data :=bytes.Join(temp,[]byte{})
	hash:=sha256.Sum256(data)
	block.Hash=hash[:]
	block.setMerKleRoot()
	return  &block
}
//对区块编解码
func (block *Block)Serialize()[]byte  {//编码
	var buffer bytes.Buffer
	encoder :=gob.NewEncoder(&buffer)
	err:= encoder.Encode(&block)
	if err!=nil {
		log.Panic(err)
	}
	return  buffer.Bytes()
}
func DeSerialize(data []byte) *Block  {
	var block Block
	decoder := gob.NewDecoder(bytes.NewReader(data))
	err := decoder.Decode(&block)
	if err!=nil {
		log.Panic(err)
	}
	return &block
}

func (block *Block)setMerKleRoot()  {
	var Merk =[][]byte{
		block.Hash,
	}
	var buffer bytes.Buffer
	encoder :=gob.NewEncoder(&buffer)
	err:= encoder.Encode(&block.Records)
	if err!=nil {
		log.Panic(err)
	}
	Merk = append(Merk, buffer.Bytes())
	data := bytes.Join(Merk,[]byte{})
	hash := sha256.Sum256(data)
	block.MerKleRoot = hash[:]
}
//uitl
func uintToByte(num uint64)[]byte {
	//uint64 -> []byte
	var buffer bytes.Buffer
	err := binary.Write(&buffer, binary.BigEndian, num)
	if err!=nil {
		log.Panic(err)
	}
	return buffer.Bytes()
}
func ByteTouint(data []byte)uint64  {
	buffer := bytes.NewBuffer(data)
	var x uint64
	err := binary.Read(buffer,binary.BigEndian,&x)
	if err!=nil {
		log.Panic(err)
	}
	return x
}