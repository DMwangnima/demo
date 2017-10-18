package main

import (
	"github.com/micro/cli"
	"share/pb"
	"github.com/micro/go-micro/server"
	"user-status-srv/handler"
	"github.com/micro/go-micro"
	"log"
	"user-srv/db"
	"share/config"
	"share"
	"time"
)

func main() {

	// 创建Service，并定义一些参数
	service := micro.NewService(
		micro.Name(config.Namespace+"userStatus"),
		micro.Version("latest"),
	)
	// 定义Service动作操作
	service.Init(
		micro.Action(func(c *cli.Context) {
			log.Println("micro.Action test ...")
			// 注册redis
			redisPool := share.NewRedisPool(3, 3, 1,300*time.Second,":6379","redis")
			// 先注册db
			db.Init(config.MysqlDSN)
			pb.RegisterUserStatusHandler(service.Server(), handler.NewUserStatusHandler(redisPool), server.InternalHandler(true))
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

	log.Println("启动user-status-srv服务 ...")

	//启动service
	if err := service.Run(); err != nil {
		log.Panic("user-status-srv服务启动失败 ...")
	}
}
