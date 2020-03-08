package gcd

import java.io.File
import chisel3.util._
import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

class DECODERUnitTester(c: DECODER) extends PeekPokeTester(c) {
    private val mDecoder = c
    poke(c.io.in,0)
    step(1)
    println()
    println()
    println("Out is " + peek(c.io.out).toString)
    poke(c.io.in,7)
    step(1)
    println("Out is " + peek(c.io.out).toString)
    // poke(c.io.in,"b010".U(3.W))
    // step(1)
    // println("Out is" + peek(c.io.out).toString)
    // poke(c.io.in,"b011".U(3.W))
    // step(1)
    // println("Out is" + peek(c.io.out).toString)
    // poke(c.io.in,"b100".U(3.W))
    // poke(c.io.in,"b101".U(3.W))
    // poke(c.io.in,"b110".U(3.W))
    // poke(c.io.in,"b111".U(3.W))

    // for(i <- 0 to 7){
        
    // }
}

class DECODERTester extends ChiselFlatSpec {
  // Disable this until we fix isCommandAvailable to swallow stderr along with stdout
  private val backendNames = if(firrtl.FileUtils.isCommandAvailable(Seq("verilator", "--version"))) {
    Array("firrtl", "verilator")
  }
  else {
    Array("firrtl")
  }
  for ( backendName <- backendNames ) {
    "DECODER" should s"calculate proper greatest common denominator (with $backendName)" in {
      Driver(() => new DECODER, backendName) {
        c => new DECODERUnitTester(c)
      } should be (true)
    }
  }

  "Basic test using Driver.execute" should "be used as an alternative way to run specification" in {
    iotesters.Driver.execute(Array(), () => new DECODER) {
      c => new DECODERUnitTester(c)
    } should be (true)
  }

  if(backendNames.contains("verilator")) {
    "using --backend-name verilator" should "be an alternative way to run using verilator" in {
      iotesters.Driver.execute(Array("--backend-name", "verilator"), () => new DECODER) {
        c => new DECODERUnitTester(c)
      } should be(true)
    }
  }

  "running with --is-verbose" should "show more about what's going on in your tester" in {
    iotesters.Driver.execute(Array("--is-verbose"), () => new DECODER) {
      c => new DECODERUnitTester(c)
    } should be(true)
  }

  /**
    * By default verilator backend produces vcd file, and firrtl and treadle backends do not.
    * Following examples show you how to turn on vcd for firrtl and treadle and how to turn it off for verilator
    */

  "running with --generate-vcd-output on" should "create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on", "--target-dir", "test_run_dir/make_a_vcd", "--top-name", "make_a_vcd"),
      () => new DECODER
    ) {

      c => new DECODERUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_a_vcd/make_a_vcd.vcd").exists should be (true)
  }

  "running with --generate-vcd-output off" should "not create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "off", "--target-dir", "test_run_dir/make_no_vcd", "--top-name", "make_no_vcd",
      "--backend-name", "verilator"),
      () => new DECODER
    ) {

      c => new DECODERUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_no_vcd/make_a_vcd.vcd").exists should be (false)

  }

}