package handler

import (
	"share/pb"
	"context"
)

//GetConnectorAddr GetConnectorAddr
func (s *UserStatusHandler) GetConnectorAddr(ctx context.Context, req *pb.GetConnectorAddrReq, rsp *pb.GetConnectorAddrRep) error {
	return nil
}

//UpdateConnectorAddr UpdateConnectorAddr
func (s *UserStatusHandler) UpdateConnectorAddr(ctx context.Context, req *pb.UpdateConnectorAddrReq, rsp *pb.UpdateConnectorAddrRep) error {
	return nil
}

// UserConnected UserConnected
func (s *UserStatusHandler) UserConnected(ctx context.Context, req *pb.UserConnectedReq, rsp *pb.UserConnectedRep) error {
	return nil
}

// UserDisonnected UserDisonnected
func (s *UserStatusHandler) UserDisonnected(ctx context.Context, req *pb.UserDisonnectedReq, rsp *pb.UserDisonnectedRep) error {
	return nil
}