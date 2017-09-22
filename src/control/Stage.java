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
                    wallCount++;
                }
                for(i=0;i<Consts.NUM_CELLS;i++){
                    wallCords[19][i]=true;
                    wallCount++;
                }
       // }
        
    }
    
    
    public int wallNumber(){
        return wallCount;
    }
    
    
  
}
