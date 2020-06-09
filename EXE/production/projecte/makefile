all: driversGenerals driversLZSS driversLZW driversLZ78 driversJPEG

driversLZSS: 
	javac -d ../EXE	model/lzss/DriverLZSS.java
	javac -d ../EXE model/lzss/DriverLZSSEncoder.java
	javac -d ../EXE model/lzss/DriverLZSSDecoder.java
	
driversLZW:
	javac -d ../EXE	model/lzw/DriverLZW.java
	javac -d ../EXE model/lzw/DriverEncoder.java
	javac -d ../EXE model/lzw/DriverDecoder.java

driversLZ78:
	javac -d ../EXE model/lz78/Driver_LZ78.java
	javac -d ../EXE model/lz78/Driver_DecoderLZ78.java
	javac -d ../EXE model/lz78/Driver_EncoderLZ78.java
	javac -d ../EXE model/lz78/Driver_ArbolGeneral.java

driversJPEG:
	javac -d ../EXE model/jpeg/DriverDCT.java
	javac -d ../EXE model/jpeg/DriverImatge.java
	javac -d ../EXE model/jpeg/DriverJPEG.java
	javac -d ../EXE model/jpeg/DriverQuantitzacio.java
	javac -d ../EXE model/jpeg/Driver_readPPM.java

driversGenerals:
	javac -d ../EXE model/DriverEstadistiques.java

