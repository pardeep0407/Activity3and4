// This #include statement was automatically added by the Particle IDE.
#include <InternetButton.h>
InternetButton b = InternetButton();

void setup() {

    // 1. Setup the Internet Button
    b.begin();
    Particle.function("myAnswer", myAnswer);
     Particle.function("myAnswer2", myAnswer2);
}

void loop(){
    
    if(b.buttonOn(1)){
        Particle.publish("answer","1",60,PUBLIC);
     
    }
    
    if(b.buttonOn(2)){
        Particle.publish("answer","2",60,PUBLIC);
     
    }
    
    if(b.buttonOn(3)){
        Particle.publish("answer","3",60,PUBLIC);
         }
    
    if(b.buttonOn(4)){
        Particle.publish("answer","4",60,PUBLIC);
     
    }
}

int myAnswer(String command){
    
    if(command == "true")
    {
         b.allLedsOn(0,255,255);
       Particle.publish("result",command,60,PUBLIC);
        delay(2000);
        b.allLedsOff();
    }
    
    else if(command == "false" )
    {

 Particle.publish("result",command,60,PUBLIC);
 b.allLedsOn(255,0,0);
        delay(2000);
        b.allLedsOff();
    }
    return 1;
}


int myAnswer2(String command){
    
    if(command == "1")
    {
         b.allLedsOn(0,255,255);
       Particle.publish("result2",command,60,PUBLIC);
        delay(2000);
        b.allLedsOff();
    }
    
    else if(command == "2" )
    {

 Particle.publish("result2",command,60,PUBLIC);
   button.ledOn(2, 0, 100, 100);
     button.ledOn(5, 0, 100, 100);
       button.ledOn(8, 0, 100, 100);
         button.ledOn(11, 0, 100, 100);
    
        delay(2000);
        b.allLedsOff();
    }
    return 1;
}


