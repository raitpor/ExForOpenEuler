package tlb;

/**
 * @author Ayase
 * @date 2021/4/27-17:20
 */
public class TLB {
    private int pageId;
    private int pageFrameId;

    public TLB(int pageId, int pageFrameId) {
        this.pageId = pageId;
        this.pageFrameId = pageFrameId;
    }

    public int getPageId() {
        return pageId;
    }

    public int getPageFrameId() {
        return pageFrameId;
    }

    @Override
    public String toString() {
        return "TLB{" +
                "pageId=" + pageId +
                ", pageFrameId=" + pageFrameId +
                '}';
    }
}
