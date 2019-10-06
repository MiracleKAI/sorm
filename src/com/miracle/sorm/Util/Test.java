package com.miracle.sorm.Util;

import com.miracle.sorm.core.Query;
import com.miracle.sorm.core.QueryFactory;

import java.util.List;

public class Test {

    public static void main(String[] args){

        for(int i = 100; i > 0; i--){
          new QueryThread().start();

        }
    }

    static class QueryThread extends Thread {

        @Override
        public void run() {
            Query query = QueryFactory.creatQuery();



        }
    }
}
