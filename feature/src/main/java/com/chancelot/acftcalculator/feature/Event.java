package com.chancelot.acftcalculator.feature;

public class Event {
    public double raw;
    public int pts;
    public String event;
    private double ptscores[] = {4.6,5.3,5.6,5.9,6.2,6.5,7.0,7.5,8.0,8.3,8.5,8.6,8.8,8.9,9.1,9.2,9.4,9.5,9.7,9.8,10.0,10.1,10.3,10.4,10.6,10.7,10.9,11.0,11.2,11.3,11.5,11.6,11.8,11.9,12.1,12.3,12.5,12.8,13.0,13.2,13.5};
    private int puscores[] = {10,12,14,16,18,20,22,24,26,28,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,52,54,56,58,60,62,64,66,68,70};
    private int sdcscores[] = {215,205,195,185,175,165,157,150,143,136,129,128,127,126,125,124,123,122,121,120,119,118,117,116,115,114,113,112,111,110,109,108,107,106,105,104,103,102,101,-1,100};
    private int runscores[] = {1267,1245,1230,1220,1210,1140,1130,1115,1100,1090,1080,1070,1060,1050,1040,1030,1020,1010,1000,900,980,970,960,950,940,930,920,910,900,890,880,870,860,850,840,830,820,810,795,780,765};

    public Event(String _event){
        event = _event;
        raw = -1;
        pts = -1;
    }

    public void convert(String text)
    {
        if (!text.isEmpty() && text != null && text != "") {
            if(event=="sd" || event=="pu" || event=="lt")
                raw = Integer.parseInt(text);
            else if(event == "pt")
                raw = Double.parseDouble(text);
            else if(event == "sdc" || event == "run"){
                int min = 0;
                int s = -1;

                if(text.matches("([0-9]|([0-5][0-9]))[:][0-5][0-9]")) {
                        String minString = text.substring(0, text.indexOf(':'));
                        String secString = text.substring(text.indexOf(':')+1, text.length());
                        min = Integer.parseInt(minString);
                        s = Integer.parseInt(secString);
                }

                raw = 60*min + s;
            }
        }
        else
            raw = -1;
        score();
    }

    public void score() {
        pts = 0;
        if(raw <0)
            pts = -1;
        else if(event=="sd"){
            raw = raw - raw % 10;
            if(raw >= 340)
                pts = 100;
            else if(raw >=330)
                pts = 99;
            else if(raw >= 140 && raw < 150)
                pts = 60;
            else if(raw >= 150 && raw < 160)
                pts = 63;
            else if(raw >= 160 && raw < 170)
                pts = 65;
            else if(raw >= 170 && raw < 330)
                pts = (int)raw/5 + 34;
        } else if(event=="pt"){
            for(int i = 0; i<ptscores.length; i++) {
                if(raw>=ptscores[i])
                    pts = i+60;
            }
        } else if (event=="pu"){
            for(int i = 0; i<puscores.length; i++) {
                if(raw>=puscores[i])
                    pts = i+60;
            }
        } else if (event=="sdc") {
            for(int i = 0; i<sdcscores.length; i++) {
                if(raw<=sdcscores[i])
                    pts = i+60;
            }
        } else if(event=="lt"){
            if(raw==0)
                pts = 0;
            else if(raw==1)
                pts = 60;
            else if(raw==2)
                pts = 63;
            else if(raw==3)
                pts = 65;
            else if(raw==4)
                pts = 67;
            else if(raw>=5 && raw <20)
                pts = 2* (int)raw + 60;
            else
                pts = 100;
        } else if(event=="run"){
            for(int i = 0; i<runscores.length; i++) {
                if(raw<=runscores[i] && i!=39)
                    pts = i+60;
            }
        }
    }
}
