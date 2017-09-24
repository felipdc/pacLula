package control;

import utils.Consts;

public class Stage{
    
    private int stageLevel;
    private int wallCount=0;
    public boolean[][] wallCords = new boolean[Consts.NUM_CELLS][Consts.NUM_CELLS];
    
    public String showStageLevel(){
        
        String stageLv = String.valueOf(stageLevel);
        return stageLv;
        
    }
    
    public void increaseStage(){
        
        stageLevel++;
        
    }
    
    public String retrieveGround(int stageLevel){
        
        switch (stageLevel){
            
            case 1:
                return "ground1.png";
            case 2:
                return "ground2.png";
        }
        
        return "";
        
    }
    
    public void generateWallCord(int stageLevel){     
        
        int i;
        
        for(i=0;i<Consts.NUM_CELLS;i++){
            for(int j=0;j<Consts.NUM_CELLS;j++){
                wallCords[i][j]=false;
            }
        }
        //switch(stageLevel){
            //case 1:
                for(i=0;i<Consts.NUM_CELLS;i++){
                    wallCords[0][i]=true;
                    wallCords[19][i]=true;
                    wallCount=wallCount+2;
                }
    
                for(i=0;i<Consts.NUM_CELLS;i++){
                    if(i==10){
                        continue;
                    }
                    wallCords[i][0]=true;
                    wallCords[i][19]=true;
                    wallCount=wallCount+2;
                }
                
                for(i=2;i<18;i++){
                    if(i==10){
                        continue;
                    }
                    wallCords[i][2]=true;
                    wallCords[i][17]=true;
                    wallCount=wallCount+2;
                }
                
                 for(i=4;i<16;i++){
                    if(i==9){
                        wallCords[1][10]=true;
                        wallCords[18][10]=true;
                        wallCount=wallCount+2;
                        continue;
                    }
                    wallCords[2][i]=true;
                    wallCords[17][i]=true;
                    wallCount=wallCount+2;
                }
                 
                for(i=6;i<14;i++){
                    if(i==10){
                        continue;
                    }
                    wallCords[4][i]=true;
                    wallCords[15][i]=true;
                    wallCount=wallCount+2;
                }
                
                for(i=6;i<14;i++){
                    if(i==9){
                        continue;
                    }
                    wallCords[6][i]=true;
                    wallCords[13][i]=true;
                    wallCount=wallCount+2;
                }
                
                for(i=2;i<17;i++){
                    if(i==4||i==9||i==11||i==16){
                        continue;
                    }
                    wallCords[i][4]=true;
                    wallCords[i][15]=true;
                    wallCount=wallCount+2;
                }
                
                for(i=6;i<12;i++){
                    if(i==9){
                        wallCords[11][i]=true;
                        wallCords[9][6]=true;
                        wallCords[10][6]=true;
                        wallCords[9][11]=true;
                        wallCords[10][11]=true;
                        wallCount=wallCount+5;
                        continue;
                    }
                    wallCords[8][i]=true;
                    wallCords[11][i]=true;
                    wallCount=wallCount+2;
                }
                
                for(i=8;i<12;i++){

                    wallCords[i][13]=true;
                    wallCount=wallCount++;
                }
                
                
                
                
                
                
        //} 
             
        
    }
    
    
    public int wallNumber(){
        return wallCount;
    }
    
    
  
}
