// setting up D7 in lieu of relay
int led = D7;

void setup()
{

   // pin configuration
   pinMode(led, OUTPUT);

   // declaring a Particle.function to turn the LED on and off from the cloud.
   Particle.function("tank",tankToggle);

   // making sure LED is off when we start:
   digitalWrite(led, LOW);
   
}

void loop()
{
   // Nothing to do here
}

void stopPhoton()
{
  //TODO: stop photon
}


// External Function
int tankToggle(String photonData)
{
  //We support 2 strings
  //"t:tank#,sec,min;"   Timers: this loads timer for the tank
  //"c:tank#,sec"   Command: this turns tank# on for sec
  //"x:"   Turn all relays off, clear all timers -- TODO.
  char cmd=0x00;
  int curIdx=0;
  int endIdx=0;
  // int tankNo; // Not in use in MVP
  char chrStr[64];
  char secStr[16];
  char minStr[16];
  char photonStr[16];
  uint32_t now;

  Serial.println("tankToggle:");
  Serial.println(photonData);
  Serial.println("");

  //parse parts
  curIdx=0;
  cmd=photonData.charAt(curIdx);    //get cmd char
  curIdx=4;   //skip cmd, :, tank# and ,


  switch (cmd)
  {
    case 'x':
      stopPhoton();
      break;

    case 'c':
      curIdx=getNextVal(photonData,curIdx,';',secStr);     //get second ; term

      digitalWrite(led, HIGH);
      
      // We'll leave it on for secStr seconds...
      delay(atoi(secStr)*1000);
      
      digitalWrite(led, LOW);

      break;

    case 't':
      //timer1
      curIdx=getNextVal(photonData,curIdx,',',secStr);     //get second , term
      curIdx=getNextVal(photonData,curIdx,';',minStr);     //get min ; term
      runTimer(secStr,minStr);

      break;
  }

  //TODO: int return value

}


void runTimer(String duration, String interval) {
    int i = 10; // safety for MVP... max 10 cycles
    while(i >= 0) {
      
      digitalWrite(led, HIGH);
      
      // We'll leave it on for secStr seconds...
      delay(atoi(duration)*1000);
      
      digitalWrite(led, LOW);
      
      delay(((atoi(interval)*60) - atoi(duration))*1000);
      
      i--;
    }
    
}

int getNextVal(String data, int off, char term, char *val)
{
  //given a String and a beginning offset and a terminator character, returns the val as a char *,
  //and returns the idx of the NEXT offset (skipping the terminator)
  //if return val -1, not found

  int endOff;
  String valStr;

  //find next terminator char
  endOff=data.indexOf(term,off);
  if (endOff == -1)
  {
    return -1;
  }
  //copy string and convert to char *
  valStr=data.substring(off,endOff);
  valStr.toCharArray(val,valStr.length()+1);    //add 1 for null term
  return endOff+1;
}
