package utils;

public interface MapBricks {
    public float distance = 18;

    
    // ground base bricks
    public int brickLayer1Qty = 54;
    public float initialBrickX = 0;

    // podium qty
    public int podiumQty = 6;
    public float initialPodiumX = 1008;
    public float defaultPodiumY = 5;

    // after podium
    public int brickLayer2Qty = 12;
    public float secondBrickX = 1116;

    public int brickBaseQty = brickLayer1Qty + podiumQty + brickLayer2Qty;
    public float defaultBrickY = 5;

    // vertical bricks
    public int vertBrick1Qty = 13;
    public float vertInitialBrickX = 72;
    public float vertInitialBrickY = 23;

    public int vertBrick2Qty = 14;
    public float vertInitialBrickX2 = 324;
    public float vertInitialBrickY2 = 59;

    public int vertBrick3Qty = 14;
    public float vertInitialBrickX3 = 576;
    public float vertInitialBrickY3 = 59;

    public int vertBrickQty = vertBrick1Qty + vertBrick2Qty + vertBrick3Qty;

    // ceiling bricks
    public int ceilBrickQty = 72;
    public float ceilDefaultY = 311;
    public float ceilDefaultX = 0;

    // brick qty
    public int brickQty = brickBaseQty + vertBrickQty + ceilBrickQty;

}
