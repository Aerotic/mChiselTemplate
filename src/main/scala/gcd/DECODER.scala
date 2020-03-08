package gcd

import chisel3._
import chisel3.util._

class MVER_IO extends Bundle {
    val ver_in = Input(UInt(3.W))
    val ver_out = Output(UInt(8.W))
    val ver_clk = Clock(INPUT)
    // ver_out.setName("ver_out")
    // ver_in.setName("ver_in")
}
class MVER_MODULE extends BlackBox with HasBlackBoxResource {
    val io = new MVER_IO()
    // renameClock(Driver.implictClock,"ver_clk")
    setResource("/mver.v")
}



class DECODER extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(3.W))
    val out = Output(UInt(8.W))
  }
//   val mverm = new MVER_MODULE()
  io.out := 3.U
  switch (io.in){
    is ("b000".U(3.W){
      io.out := "b0000_0001".U(8.W)
    }
    is ("b001".U(3.W)){
      io.out := "b0000_0010".U(8.W)
    }
    is ("b010".U(3.W)){
      io.out := "b0000_0100".U(8.W)
    }
    is ("b011".U(3.W)){
      io.out := "b00001000".U(8.W)
    }
    is ("b100".U(3.W)){
      io.out := "b0001_0000".U(8.W)
    }
    is ("b101".U(3.W)){
      io.out := "b00100000".U(8.W)
    }
    is ("b110".U(3.W)){
      io.out := "b01000000".U(8.W)
    }
    is ("b111".U(3.W)){
      io.out := "b10000000".U(8.W)
    }
  }
}
