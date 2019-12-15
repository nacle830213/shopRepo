package main

import (
	"bytes"
	"fmt"
	"github.com/astaxie/beego"
	"time"
)

type DataController struct {
	beego.Controller
}
type JSONS struct {
	//必须的大写开头
	Code string
	Msg  string
	Blocks [] *Block
}
type BLOCK struct {
	//区块头
	PreBlockHash [] byte//前哈希
	MerKleRoot []byte
	TimeStamp string //时间戳

	Hash [] byte
	//区块体
	Records []*Record
}
func (c *DataController) Get()  {
	blocks := []*BLOCK{}

	it:=Bc.NewIterator()
	for{
		block := it.Next()
		cblock := BLOCK{
			PreBlockHash:block.PreBlockHash,
			MerKleRoot:block.MerKleRoot,
			Hash:block.Hash,
			Records:block.Records,
		}
		timeFormat := time.Unix(int64(block.TimeStamp),0).Format("2006-01-02 15:04:05")
		cblock.TimeStamp = timeFormat
		blocks = append(blocks, &cblock)
		if bytes.Equal(block.PreBlockHash,[]byte{}){
			fmt.Printf("遍历结束\n")
			break
		}
	}
	c.Data["json"] = map[string]interface{}{"status": 200, "message": "ok", "data": blocks}
	c.ServeJSON()
	return
}

type  TestController struct {
	beego.Controller
}

func (c *TestController)Get()  {
	Sleeptime = 10
}

type ResetController struct {
	beego.Controller
}

func (c *ResetController)Get()  {
	SaveIdindex(0)
}

