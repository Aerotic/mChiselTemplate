SBT ?= java -jar /home/rv/riscv/freedom/rocket-chip/sbt-launch.jar ++2.12.4
availible:
	${SBT} 'testOnly gcd.GCDTester -- -z Basic
sbt:
	${SBT} 'test:runMain gcd.GCDMain --generate-vcd-output on'
ver:
	${SBT} 'test:runMain gcd.DECODERMain --backend-name verilator'