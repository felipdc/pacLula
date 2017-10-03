package control;

import utils.Consts;

public class Stage{
    
    private int stageLevel;
    private int wallCount=0;
    protected int[][] wallCords = new int[Consts.NUM_CELLS][Consts.NUM_CELLS];
    
    public Stage(int level){
        generateWallCord(level);
    }
    
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
    
    //TODO reduce number of fors 
    
    public void generateWallCord(int stageLevel){     
        
        int i;
        
        for(i=0;i<Consts.NUM_CELLS;i++){
            for(int j=0;j<Consts.NUM_CELLS;j++){
                wallCords[i][j]=0;
            }
        }
        //switch(stageLevel){
            //case 1:
                for(i=0;i<Consts.NUM_CELLS;i++){
                    wallCords[0][i]=1;
                    wallCords[19][i]=1;
                    wallCount=wallCount+2;
                }
    
                for(i=0;i<Consts.NUM_CELLS;i++){
                    if(i==10){
                        continue;
                    }
                    wallCords[i][0]=1;
                    wallCords[i][19]=1;
                    wallCount=wallCount+2;
                }
                
                for(i=2;i<18;i++){
                    if(i==10){
                        continue;
                    }
                    wallCords[i][2]=1;
                    wallCords[i][17]=1;
                    wallCount=wallCount+2;
                }
                
                 for(i=4;i<16;i++){
                    if(i==9){
                        wallCords[1][10]=1;
                        wallCords[18][10]=1;
                        wallCount=wallCount+2;
                        continue;
                    }
                    wallCords[2][i]=1;
                    wallCords[17][i]=1;
                    wallCount=wallCount+2;
                }
                 
                for(i=6;i<14;i++){
                    if(i==10){
                        continue;
                    }
                    wallCords[4][i]=1;
                    wallCords[15][i]=1;
                    wallCount=wallCount+2;
                }
                
                for(i=6;i<14;i++){
                    if(i==9){
                        continue;
                    }
                    wallCords[6][i]=1;
                    wallCords[13][i]=1;
                    wallCount=wallCount+2;
                }
                
                for(i=2;i<17;i++){
                    if(i==4||i==9||i==11||i==16){
                        continue;
                    }
                    wallCords[i][4]=1;
                    wallCords[i][15]=1;
                    wallCount=wallCount+2;
                }
                
                for(i=6;i<12;i++){
                    if(i==9){
                        wallCords[11][i]=1;
                        wallCords[9][6]=1;
                        wallCords[10][6]=1;
                        wallCords[9][11]=1;
                        wallCords[10][11]=1;
                        wallCount=wallCount+5;
                        continue;
                    }
                    wallCords[8][i]=1;
                    wallCords[11][i]=1;
                    wallCount=wallCount+2;
                }
                
                for(i=8;i<12;i++){

                    wallCords[i][13]=1;
                    wallCount=wallCount++;
                }
                
                //preventing coins to initialize in ghost spawn area
                for(i=7;i<11;i++){
                    wallCords[9][i]=2;
                    wallCords[10][i]=2;
                    
                }
                wallCords[8][9]=2;
                
                
                
                
                
                
        //} 
             
        
    }
    
    
    public int getWallNumber(){
        return wallCount;
    }
    
    
  
}
