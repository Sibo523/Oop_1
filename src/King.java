public class King extends ConcretePiece{
    String name;
    public King(Player ow,String name){
        super(ow);
        this.name= name;
    }
    public String getName(){
        return name;
    }
    @Override
    public String getType() {
        return "♔";
    }
}
