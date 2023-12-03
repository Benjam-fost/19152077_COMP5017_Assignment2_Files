package comp5017_CW2;

public class StationInfo implements IStationInfo{
    private final String name;
    private final int x, y;

    public StationInfo(String name, int x, int y) {
        assert name != null && !name.isBlank();
        assert 0 <= x && x < 256 && 0 <= y && y < 256;
        this.name = name;
        this.x = x; this.y = y;
    }

    /**
     * @pre true
     * @return name of station
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @pre true
     * @return X value of station position
     */
    @Override
    public int getxPos() {
        return x;
    }

    /**
     * @pre true
     * @return Y value of station position
     */
    @Override
    public int getyPos() {
        return y;
    }
}
