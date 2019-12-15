package main

import (
	"database/sql"
	"fmt"
	_ "github.com/jinzhu/gorm/dialects/mysql"
	"log"
)

type Record struct {
	Id  int64
	UserId int64
	Good   int64
	Type   int64
	Amount int64
	Price  float64
	ToFrom sql.NullString
	Time []uint8
}

func FindeRecords( id uint64,step uint64)[]*Record  {
	db,err:=sql.Open("mysql","root:Yad@121413@tcp(117.50.97.181:3306)/repo?charset=utf8")
	if err!=nil {
		log.Panic(err)
	}
	sql :="SELECT * FROM record WHERE  id  > ? AND  id <= ? "

	stmt,err:=db.Prepare(sql) //sql
	if err!=nil {
		log.Panic(err)
	}

	rows ,err :=stmt.Query(id,id+step)
	var records []*Record
	for rows.Next(){
		record := Record{}
		err = rows.Scan(&record.Id,&record.Time,&record.UserId,&record.Good,&record.Type,&record.Amount,&record.Price,&record.ToFrom)
		if err!=nil {
			log.Panic(err)
		}
		records = append(records, &record)
		fmt.Printf("%v \n",record.Good)
	}
	//关闭连接
	defer db.Close()
	defer stmt.Close()
	defer rows.Close()
	return  records
}