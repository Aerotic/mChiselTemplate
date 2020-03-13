package gcd

import chisel3._
import chisel3.util._
import chisel3.experimental._ // To enable experimental features

// class MVER_IO extends Bundle {
//     val ver_in = Input(UInt(3.W))
//     val ver_out = Output(UInt(8.W))
//     val ver_clk = Input(Clock())
//     // ver_out.setName("ver_out")
//     // ver_in.setName("ver_in")
// }
// class MVER_MODULE extends BlackBox with HasBlackBoxResource {
//     val io = new MVER_IO()
//     // renameClock(Driver.implictClock,"ver_clk")
//     setResource("/mver.v")
// }


// module名必须与class名一致，区分大小写
class M_BlackBoxInline extends BlackBox with HasBlackBoxInline{
  val io = IO(new Bundle() {
    val ver_in = Input(UInt(3.W))
    // val in2 = Input(UInt(64.W))
    val ver_out = Output(UInt(8.W))
  })
//   addResource("mver.v")
    setInline("M_BlackBoxInline.v",    // 运行过程中会在输出文件夹中生成以此命名的verilog文件
    s"""
        |module M_BlackBoxInline(
        |    input reg [2:0] ver_in,
        |    output reg [7:0] ver_out
        |);
        |always @(*) begin
        |    case (ver_in) 
        |        3'b000: ver_out = 8'b0000_0001;
        |        3'b001: ver_out = 8'b0000_0010;
        |        3'b010: ver_out = 8'b0000_0100;
        |        3'b011: ver_out = 8'b0000_1000;
        |        3'b100: ver_out = 8'b0001_0000;
        |        3'b101: ver_out = 8'b0010_0000;
        |        3'b110: ver_out = 8'b0100_0000;
        |        3'b111: ver_out = 8'b1000_0000;        
        |        default:  ver_out = 8'b0000_0001;
        |    endcase
        |end
        |endmodule // mver
    """.stripMargin)
}


// module名必须与class名一致，区分大小写
class TriOctDecoder_Verilog extends BlackBox with HasBlackBoxResource{
  val io = IO(new Bundle() {
    val ver_in = Input(UInt(3.W)) // 这个名必须与verilog中的名字一致
    val ver_out = Output(UInt(8.W))
  })
  addResource("/TriOctDecoder_Verilog.v") // 添加源文件,路径root在test/resources内
//   setResource("/MVER1.v") 上下两种皆可，但在chisel3.2之后只能用上边那种
}

class TriOctDecoder extends Module {
    val io = IO(new Bundle {
                val in = Input(UInt(3.W))
                val out = Output(UInt(8.W))
                })
    io.out := "b0000_0000".U(8.W) // io.out初始化赋值，无此则报错
    switch (io.in){
        is ("b000".U(3.W)){
            io.out := "b0000_0001".U(8.W)
        }
        is ("b001".U(3.W)){
            io.out := "b0000_0010".U(8.W)
        }
        is ("b010".U(3.W)){
            io.out := "b0000_0100".U(8.W)
        }
        is ("b011".U(3.W)){
            io.out := "b0000_1000".U(8.W)
        }
        is ("b100".U(3.W)){
            io.out := "b0001_0000".U(8.W)
        }
        is ("b101".U(3.W)){
            io.out := "b0010_0000".U(8.W)
        }
        is ("b110".U(3.W)){
            io.out := "b0100_0000".U(8.W)
        }
        is ("b111".U(3.W)){
            io.out := "b1000_0000".U(8.W)
        }
    }
}

class DECODER extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(3.W))
    val out = Output(UInt(8.W))
  })
  val mverm = Module(new TriOctDecoder_Verilog())
  io.out := mverm.io.ver_out
  mverm.io.ver_in := io.in

}
