package main

import "github.com/astaxie/beego"

func init()  {
	beego.Router("/data",&DataController{})
	beego.Router("/test",&TestController{})
	beego.Router("/reset",&ResetController{})
}
