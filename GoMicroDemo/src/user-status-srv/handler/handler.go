package handler

import (
	"go.uber.org/zap"
	"github.com/garyburd/redigo/redis"
)
type UserStatusHandler struct {
	pool               *redis.Pool
	logger             *zap.Logger
	namespace          string
	sessionExpire int
	tokenExpire int
}

func NewUserStatusHandler(pool *redis.Pool) *UserStatusHandler {
	return &UserStatusHandler{
		pool: pool,
		sessionExpire: 15 * 86400,
		tokenExpire:   15 * 86400,
	}
}