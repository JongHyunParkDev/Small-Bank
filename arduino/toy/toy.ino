#include "LedControl.h"

byte A[8]={
  B00000000,
  B00011111,
  B00101000,
  B01001000,
  B01001000,
  B01001000,
  B00101000,
  B00011111,
};
byte B[8]={
  B00000000,
  B00110110,
  B01001001,
  B01001001,
  B01001001,
  B01001001,
  B01001001,
  B01111111,
};
byte C[8]={
  B00000000,
  B01000001,
  B01000001,
  B01000001,
  B01000001,
  B01100011,
  B00110110,
  B00011100,
};
byte D[8]={
  B00000000,
  B00011100,
  B00100010,
  B01000001,
  B01000001,
  B01000001,
  B01000001,
  B01111111,
};
byte E[8]={
  B00000000,
  B01001001,
  B01001001,
  B01001001,
  B01001001,
  B01001001,
  B01001001,
  B01111111,
};
byte F[8]={
  B00000000,
  B01001000,
  B01001000,
  B01001000,
  B01001000,
  B01001000,
  B01001000,
  B01111111,
};
byte G[8]={
  B00000000,
  B00101111,
  B01001001,
  B01001001,
  B01001001,
  B01000001,
  B00100001,
  B00011110,
};
byte H[8]={
  B00000000,
  B01111111,
  B00001000,
  B00001000,
  B00001000,
  B00001000,
  B00001000,
  B01111111,
};
byte I[8]={
  B00000000,
  B01000001,
  B01000001,
  B01000001,
  B01111111,
  B01000001,
  B01000001,
  B01000001,
};
  byte J[8]={
  B00000000,
  B01111110,
  B01000001,
  B01000001,
  B01000001,
  B00000001,
  B00000010,
  B00001100,
};
byte K[8]={
  B00000000,
  B01000001,
  B00100010,
  B00010100,
  B00001000,
  B00001000,
  B00001000,
  B01111111,
};
byte L[8]={
  B00000000,
  B00000001,
  B00000001,
  B00000001,
  B00000001,
  B00000001,
  B00000001,
  B01111111,
};
  byte M[8]={
  B00000000,
  B01111111,
  B00100000,
  B00010000,
  B00001000,
  B00010000,
  B00100000,
  B01111111,
};
byte N[8]={
  B00000000,
  B01111111,
  B00000010,
  B00000100,
  B00001000,
  B00010000,
  B00100000,
  B01111111,
};
byte O[8]={
  B00000000,
  B00011100,
  B00100010,
  B01000001,
  B01000001,
  B01000001,
  B00100010,
  B00011100,
};
byte P[8]={
  B00000000,
  B00110000,
  B01001000,
  B01001000,
  B01001000,
  B01001000,
  B01001000,
  B01111111,
};
byte Q[8]={
  B00000000,
  B00011101,
  B00100010,
  B01000101,
  B01001001,
  B01000001,
  B00100010,
  B00011100,
};
byte R[8]={
  B00000000,
  B00110001,
  B01001010,
  B01001100,
  B01001000,
  B01001000,
  B01001000,
  B01111111,
};
byte S[8]={
  B00000000,
  B01000110,
  B01001001,
  B01001001,
  B01001001,
  B01001001,
  B01001001,
  B00110001,
};
byte T[8]={
  B00000000,
  B01000000,
  B01000000,
  B01000000,
  B01111111,
  B01000000,
  B01000000,
  B01000000,
};
byte U[8]={
  B00000000,
  B01111100,
  B00000010,
  B00000001,
  B00000001,
  B00000001,
  B00000010,
  B01111100,
};
byte V[8]={
  B00000000,
  B01111000,
  B00000100,
  B00000010,
  B00000001,
  B00000010,
  B00000100,
  B01111000,
};
byte W[8]={
  B00000000,
  B01111111,
  B00000010,
  B00000100,
  B00001000,
  B00000100,
  B00000010,
  B01111111,
};
byte X[8]={
  B00000000,
  B01000001,
  B00100010,
  B00010100,
  B00001000,
  B00010100,
  B00100010,
  B01000001,
};
byte Y[8]={
  B00000000,
  B01000000,
  B00100000,
  B00010000,
  B00001111,
  B00010000,
  B00100000,
  B01000000,
};
byte Z[8]={
  B00000000,
  B01000001,
  B01100001,
  B01010001,
  B01001001,
  B01000101,
  B01000011,
  B01000001,
};
  byte CAT[8]={
  B00000000,
  B01111110,
  B00100001,
  B00010101,
  B00010001,
  B00010101,
  B00100001,
  B01111110,
};
  byte HEART[8]={
  B00000000,
  B00111000,
  B01000100,
  B00100010,
  B00010001,
  B00100010,
  B01000100,
  B00111000,
};
  byte DEATH[8]={
  B00000000,
  B00011100,
  B00100011,
  B01001001,
  B01000011,
  B01001001,
  B00100011,
  B00011100,
};
byte YANGMI[8]={
  B00000000,
  B00001110,
  B11010001,
  B00010101,
  B00010001,
  B00010101,
  B11010001,
  B00001110,
};
byte SUNNY[8]={
  B00001000,
  B01010010,
  B00111100,
  B10111110,
  B01111101,
  B00111100,
  B01001010,
  B00010000,
};
byte RAINNY[8]={
  B01100011,
  B11111000,
  B11100000,
  B11101100,
  B01100000,
  B11100110,
  B11100000,
  B01101100,
};
byte VOID[8]={
  B00000000,
  B00000000,
  B00000000,
  B00000000,
  B00000000,
  B00000000,
  B00000000,
  B00000000,
};
byte SLASH[8]={
  B00000000,
  B00000000,
  B11000000,
  B00110000,
  B00001100,
  B00000011,
  B00000000,
  B00000000,
};
byte ZERO[8]={
  B00000000,
  B01111110,
  B10000001,
  B10000001,
  B10000001,
  B10000001,
  B01111110,
  B00000000,
};
byte ONE[8]={
  B00000000,
  B00000000,
  B00000001,
  B11111111,
  B01000001,
  B00000000,
  B00000000,
  B00000000,
};
byte TWO[8]={
  B00000000,
  B01000001,
  B10100001,
  B10010001,
  B10001001,
  B10000101,
  B01000011,
  B00000000,
};
byte THREE[8]={
  B00000000,
  B01000110,
  B10101001,
  B10010001,
  B10010001,
  B10000001,
  B01000010,
  B00000000,
};
byte FOUR[8]={
  B00000000,
  B00000100,
  B11111111,
  B01000100,
  B00100100,
  B00010100,
  B00001100,
  B00000000,
};
byte FIVE[8]={
  B00000000,
  B10001100,
  B10010010,
  B10010001,
  B10010001,
  B10010001,
  B11110010,
  B00000000,
};
byte SIX[8]={
  B00000000,
  B00001110,
  B00010001,
  B10010001,
  B01010001,
  B00110001,
  B00011110,
  B00000000,
};
byte SEVEN[8]={
  B00000000,
  B11100000,
  B10010000,
  B10001000,
  B10000111,
  B10000000,
  B11000000,
  B00000000,
};
byte EIGHT[8]={
  B00000000,
  B01101110,
  B10010001,
  B10010001,
  B10010001,
  B10010001,
  B01101110,
  B00000000,
};
byte NINE[8]={
  B00000000,
  B01111000,
  B10001100,
  B10001010,
  B10001001,
  B10001000,
  B01110000,
  B00000000,
};

int repeat = 3;

LedControl lc=LedControl(12,11,10,1);
unsigned long delaytime=1000;

void setup() {
  Serial.begin(9600); // 시리얼 통신 속도 설정

  lc.shutdown(0,false);
  /* Set the brightness to a medium values */
  lc.setIntensity(0,8);
  /* and clear the display */
  lc.clearDisplay(0);
}

void loop() {
  if (Serial.available()) {
    String str = Serial.readString();
    int length = str.length();
    char ch[length] = {};
    str.toCharArray(ch, length);

    alarm();
    
    for (int j = 0; j < repeat; j++) {
      for (int i = 0; i < sizeof(ch); i++) {
        displayDotMatrix(ch[i]);
      }
    }
  }
}

void displayDotMatrix(char c) {
  byte temp [8];
  switch (c) {
    case 'A': memcpy(temp, A, 8); break;
    case 'B': memcpy(temp, B, 8); break;
    case 'C': memcpy(temp, C, 8); break;
    case 'D': memcpy(temp, D, 8); break;
    case 'E': memcpy(temp, E, 8); break;
    case 'F': memcpy(temp, F, 8); break;
    case 'G': memcpy(temp, G, 8); break;
    case 'H': memcpy(temp, H, 8); break;
    case 'I': memcpy(temp, I, 8); break;
    case 'J': memcpy(temp, J, 8); break;
    case 'K': memcpy(temp, K, 8); break;
    case 'L': memcpy(temp, L, 8); break;
    case 'M': memcpy(temp, M, 8); break;
    case 'N': memcpy(temp, N, 8); break;
    case 'O': memcpy(temp, O, 8); break;
    case 'P': memcpy(temp, P, 8); break;
    case 'Q': memcpy(temp, Q, 8); break;
    case 'R': memcpy(temp, R, 8); break;
    case 'S': memcpy(temp, S, 8); break;
    case 'T': memcpy(temp, T, 8); break;
    case 'U': memcpy(temp, U, 8); break;
    case 'V': memcpy(temp, V, 8); break;
    case 'W': memcpy(temp, W, 8); break;
    case 'X': memcpy(temp, X, 8); break;
    case 'Y': memcpy(temp, Y, 8); break;
    case 'Z': memcpy(temp, Z, 8); break;
    case '0': memcpy(temp, ZERO, 8); break;
    case '1': memcpy(temp, ONE, 8); break;
    case '2': memcpy(temp, TWO, 8); break;
    case '3': memcpy(temp, THREE, 8); break;
    case '4': memcpy(temp, FOUR, 8); break;
    case '5': memcpy(temp, FIVE, 8); break;
    case '6': memcpy(temp, SIX, 8); break;
    case '7': memcpy(temp, SEVEN, 8); break;
    case '8': memcpy(temp, EIGHT, 8); break;
    case '9': memcpy(temp, NINE, 8); break;
    case '/': memcpy(temp, SLASH, 8); break;
    case ' ': memcpy(temp, VOID, 8); break;
    case '!': memcpy(temp, CAT, 8); break;
    case '@': memcpy(temp, HEART, 8); break;
    case '#': memcpy(temp, DEATH, 8); break;
    case '$': memcpy(temp, YANGMI, 8); break;
    case '%': memcpy(temp, SUNNY, 8); break;
    case '^': memcpy(temp, RAINNY, 8); break;
    default: memcpy(temp, VOID, 8); break;
  }
  for (int i=0; i<8; i++) {
    lc.setRow(0,i,temp[i]);    
  }
  delay(delaytime);
}

void alarm() {
  noTone(9);
  tone(9, 200, 100);
  delay(100);
  noTone(9);
  tone(9, 300, 100);
  delay(100);
}