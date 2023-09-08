package com.dev.was.service;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class SerialService {
    SerialPort[] ports = SerialPort.getCommPorts();
    SerialPort serialPort = null;

    @PostConstruct
    public void init () {
        if (ports.length == 0) {
            logger.info("No serial ports available. Exiting.");
            return;
        }

        for (int i = 0; i < ports.length; i++) {
            String portName = ports[i].getSystemPortName();
            if (portName.equals(ARDUINO_PORT_STR)) {
                serialPort = ports[i];
            }
        }
    }

    public synchronized void sendSerialMsg(String sendMsg) throws InterruptedException {
        // 마지막 String 짤림
        sendMsg = sendMsg.toUpperCase() + " ";
        if (serialPort.openPort()) {
            logger.info("Serial port connected.");

            // 시리얼 포트의 설정을 구성합니다.
            serialPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);

            // 시리얼 포트에서 데이터를 수신하는 쓰레드를 시작합니다.
            // 데이터 수신 일단 하지 않는다.
//            serialPort.addDataListener(new SerialPortDataListener() {
//                @Override
//                public int getListeningEvents() {
//                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
//                }
//
//                @Override
//                public void serialEvent(SerialPortEvent event) {
//                    if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
//                        byte[] newData = new byte[serialPort.bytesAvailable()];
//                        int numRead = serialPort.readBytes(newData, newData.length);
//                        System.out.print("Received data: ");
//                        System.out.write(newData, 0, numRead);
//                        System.out.println();
//                    }
//                }
//            });
            // 딜레이 타임이 있다.
            Thread.sleep(sleepTimeSec);

            // 데이터를 시리얼 포트로 송신합니다.
            serialPort.writeBytes(sendMsg.getBytes(), sendMsg.length());

            // 몇 초 동안 대기한 후 시리얼 포트를 닫습니다.
            Thread.sleep(sleepTimeSec);
            serialPort.closePort();

            logger.info("Serial port closed.");
        } else {
            logger.info("Failed to open serial port.");
        }
    }

    private final long sleepTimeSec = 3000;
    private static final String ARDUINO_PORT_STR = "COM6";
    private static final Logger logger = LoggerFactory.getLogger(SerialService.class);
}
