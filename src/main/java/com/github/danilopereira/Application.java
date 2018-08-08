package com.github.danilopereira;

import com.github.danilopereira.verticles.TravelCompanyVerticle;

public class Application {
    public static void main(String [] args){
        TravelCompanyVerticle serverVerticle = new TravelCompanyVerticle();
        try {
            serverVerticle.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
