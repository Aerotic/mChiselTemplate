package gcd

import chisel3._

object DECODERMain extends App {
    iotesters.Driver.execute(args, () => new DECODER) {
    c => new DECODERUnitTester(c)
  }
}
object DECODERRepl extends App {
  iotesters.Driver.executeFirrtlRepl(args, () => new DECODER)
}