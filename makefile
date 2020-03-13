
Path2SbtJar = /home/rv/riscv/freedom/rocket-chip/sbt-launch.jar
SBT ?= java -jar ${Path2SbtJar} ++2.12.4
availible:
	${SBT} 'testOnly gcd.GCDTester -- -z Basic
sbt:
	${SBT} 'test:runMain gcd.GCDMain --generate-vcd-output on'
sim:
	${SBT} 'test:runMain gcd.DECODERMain --backend-name verilator'
clean:
	${SBT} clean