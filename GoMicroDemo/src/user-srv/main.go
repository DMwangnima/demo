package main

import (
	"github.com/micro/cli"
	"mewe_job/GoMicroDemo/src/share/pb"
	"github.com/micro/go-micro/server"
	"mewe_job/GoMicroDemo/src/user-srv/handler"
	"github.com/micro/go-micro"
	"log"
	"mewe_job/GoMicroDemo/src/user-srv/db"
	"mewe_job/GoMicroDemo/src/share/config"
)

func main() {


	// 创建Service，并定义一些参数
	service := micro.NewService(
		micro.Name(config.Namespace+"user"),
		micro.Version("latest"),
	)

	// 定义Service动作操作
	service.Init(
		micro.Action(func(c *cli.Context) {
			log.Println("micro.Action test ...")
			// 先注册db
			db.Init(config.MysqlDSN)
			pb.RegisterUserServiceHandler(service.Server(), handler.NewUserHandler(), server.InternalHandler(true))
		}),
		micro.AfterStop(func() error {
			log.Println("micro.AfterStop test ...")
			return nil
		}),
		micro.AfterStart(func() error {
			log.Println("micro.AfterStart test ...")
			return nil
		}),
	)

	log.Println("启动user-srv服务 ...")

	//启动service
	if err := service.Run(); err != nil {
		log.Panic("user-srv服务启动失败 ...")
	}
}
