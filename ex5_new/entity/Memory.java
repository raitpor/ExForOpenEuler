package entity;

/**
 * @author Ayase
 * @date 2021/5/23-10:22
 */
public class Memory {
    private PageFrame[] pageFrames;

    public Memory(int size){
        pageFrames = new PageFrame[size];
        for(int i = 0; i < size ; i++){
            pageFrames[i] = new PageFrame();
        }
    }

    /***********************************************************
     * @MethodName getPageFrames
     * @Description TODO
     * @Param []
     * @Return entity.PageFrame[]
     * @author Ayase
     *********************************************************/
    public PageFrame[] getPageFrames() {
        PageFrame[] temp = new PageFrame[pageFrames.length];
        for(int i = 0 ; i < temp.length ; i++){
            temp[i] = new PageFrame(pageFrames[i]);
        }
        return temp;
    }

    /***********************************************************
     * @MethodName setPageFrame
     * @Description TODO
     * @Param [pageFrames]
     * @Return void
     * @author Ayase
     *********************************************************/
    public void setPageFrame(int n,char[] data) {
        if (n >this.pageFrames.length){
            throw new IndexOutOfBoundsException("索引n大于内存大小" + n);
        }
        this.pageFrames[n].setData(data);
    }

    /***********************************************************
     * @MethodName clear
     * @Description TODO 清除指定索引页框
     * @Param [n]
     * @Return void
     * @author Ayase
     *********************************************************/
    public void clear(int n){
        pageFrames[n].clear();
    }

    public void printData(){
        for (int i = 0 ; i < pageFrames.length ; i++){
            pageFrames[i].printData();
        }
        System.out.println();
    }

    public void printData(int n){
        System.out.print(n);
        pageFrames[n].printData();
    }

    @Override
    public String toString(){
        StringBuffer str = new StringBuffer();

        for(int i = 0 ; i < pageFrames.length ; i++){
            str.append(pageFrames[i].toString());
        }

        return str.toString();
    }

    public char getData(int realAddr){
        return pageFrames[realAddr/PageFrame.PAGE_SIZE].getData()[realAddr%PageFrame.PAGE_SIZE];
    }
}
