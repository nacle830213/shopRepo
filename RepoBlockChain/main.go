package main

import (
	"bytes"
	"fmt"
	"github.com/astaxie/beego"
	"github.com/astaxie/beego/plugins/cors"
	"time"
)

var Bc BlockChain
var Sleeptime time.Duration =60
var Id uint64 = 0
func main()  {
	Bc=*CreateBlockChain()
	go MakeRecordBlock(&Bc)
	defer Bc.db.Close()
	beego.InsertFilter("*", beego.BeforeRouter, cors.Allow(&cors.Options{
		AllowAllOrigins:  true,
		AllowMethods:     []string{"GET", "POST", "PUT", "DELETE", "OPTIONS"},
		AllowHeaders:     []string{"Origin", "Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Content-Type"},
		ExposeHeaders:    []string{"Content-Length", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Content-Type"},
		AllowCredentials: true,
	}))
	beego.Run("localhost:8000")
}

func MakeRecordBlock(bc *BlockChain)  {
	var id uint64 = GetIdindex()
	var recordStep uint64 = 2
	fmt.Printf("id%v",id)
	for{
		records:=FindeRecords(id,recordStep)//一次最多读取   recordStep 条交易记录
		len := len(records)
		if len>0 && len<=2 {
			id +=uint64(len)
			bc.AddBlock(records)
			fmt.Printf("%v\n",id)
			SaveIdindex(id)
		}
		fmt.Printf("%vwawawawa\n",id)

		time.Sleep(Sleeptime*time.Second)
	}
}

func display(bc *BlockChain)  {
	it:=bc.NewIterator()
	for{
		block := it.Next()
		fmt.Printf("++++++++++++++++++++++++++++\n")
		fmt.Printf("PreBlockHash:%x\n",block.PreBlockHash)
		fmt.Printf("MerKleRoot::%x\n",block.MerKleRoot)
		timeFormat := time.Unix(int64(block.TimeStamp),0).Format("2006-01-02 15:04:05")
		fmt.Printf("TimeStamp:%s\n",timeFormat)
		fmt.Printf("Hash::%x\n",block.Hash)
		records := block.Records
		for _,v:=range  records {
			fmt.Printf("%v %v %v %v\n",v.Id,v.UserId,v.Type,v.Good)
		}

		if bytes.Equal(block.PreBlockHash,[]byte{}){
			fmt.Printf("遍历结果\n")
			break
		}
	}
}