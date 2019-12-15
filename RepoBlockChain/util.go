package main

import (
	"database/sql"
	"log"
)

func SaveIdindex(id uint64) {
	db,err:=sql.Open("mysql","root:Yad@121413@tcp(117.50.97.181:3306)/repo?charset=utf8")
	if err!=nil {
		log.Panic(err)
	}
	sql :="UPDATE  goods set amount = ? where  id = 2 "

	stmt,err:=db.Prepare(sql) //sql
	if err!=nil {
		log.Panic(err)
	}
	rows ,err :=stmt.Query(id)
	//关闭连接
	defer db.Close()
	defer stmt.Close()
	defer rows.Close()
}
func GetIdindex()uint64  {
	var IdIndex uint64
	db,err:=sql.Open("mysql","root:Yad@121413@tcp(117.50.97.181:3306)/repo?charset=utf8")
	if err!=nil {
		log.Panic(err)
	}
	sql :="Select  * from  goods where  id=2"

	stmt,err:=db.Prepare(sql) //sql
	if err!=nil {
		log.Panic(err)
	}
	rows ,err :=stmt.Query()
	for rows.Next(){
		var ids uint64
		var name string
		var price float64
		var modified_time string
		var create_time string
		err = rows.Scan(&ids,&name,&price,&IdIndex,&modified_time,&create_time)
		if err!=nil {
			log.Panic(err)
		}
	}
	//关闭连接
	defer db.Close()
	defer stmt.Close()
	defer rows.Close()
	return  IdIndex
}

