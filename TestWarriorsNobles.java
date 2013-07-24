import java.util.ArrayList;
/**
 * @version NYU-Poly: CS9053 Intro to Java
 * @author Shulin Zhang 0494786
 */
class TestWarriorsNobles{
	public static void main(String[] args){
		Noble art = new Noble("King Arthur");
        Noble lance = new Noble("Lancelot du Lac");
        Noble jim = new Noble("Jim");
        Noble linus = new Noble("Linus Torvalds");
        Noble billie = new Noble("Bill Gates");

        Warrior cheetah = new Warrior("Tarzan", 10);
        Warrior wizard = new Warrior("Merlin", 15);
        Warrior theGovernator = new Warrior("Conan", 12);
        Warrior nimoy = new Warrior("Spock", 15);
        Warrior lawless = new Warrior("Xena", 20);
        Warrior mrGreen = new Warrior("Hulk", 8);
        Warrior dylan = new Warrior("Hercules", 3);

        jim.hire(nimoy);
        lance.hire(theGovernator);
        art.hire(wizard);
        lance.hire(dylan);
        linus.hire(lawless);
        billie.hire(mrGreen);
        art.hire(cheetah);

        System.out.println(jim);
        System.out.println(lance);
        System.out.println(art);
        System.out.println(linus);
        System.out.println(billie);

        cheetah.runaway();
        System.out.println(art);

        art.battle(lance);
        jim.battle(lance);
        linus.battle(billie);
        billie.battle(lance);
        
        System.out.println("====================");
        System.out.println(jim);
        System.out.println(lance);
        System.out.println(art);
        System.out.println(linus);
        System.out.println(billie);
    }
}
/**
 * A <code>Noble</code> object represents a Noble.
 * A noble has a name, a warrior army (a group of warriors) and a status (alive or dead).
 * A noble can declare a battle with another noble, such as "art.battle(lance)".
 * A noble can hire one or more warriors as his army, such as "jim.hire(nimoy)".
 * A noble can delete a warrior from his army, when the warrior want to quit (run away).
 * A noble can list his army using "toString()".
 */
class Noble{
	private String name;
	private ArrayList<Warrior> army;
	private boolean isDead;
	
	public Noble(String name){
		this.name = name;
		isDead = false;
		army = new ArrayList<Warrior>();
	}
	/**
	 * Hires the warrior and adds it to army.
	 * The noble can hire the warrior, only when the noble is alive, 
	 * the warrior is alive and the warrior doesn't have an owner.
	 * @param warrior going to be hired warrior
	 */
	public void hire(Warrior warrior){
		Warrior warriorArmy = warrior;
		if(!isDead){
			if(!warriorArmy.isDead()){
				if(!warriorArmy.isHired()){
					army.add(warriorArmy);
					warriorArmy.hiredBy(this);
				}
				else{
					System.out.println(warriorArmy.getWarriorName() + "is hired and cannot be hired again");
				}
			}
			else{
				System.out.println(warriorArmy.getWarriorName() + "is dead and cannot be hired");
			}
		}
		else{
			System.out.println(name + "is dead and cannot hire warrior.");
		}
	}
	/**
	 * A noble battles another noble.
	 * <p>1) When they were both dead, print "Oh, NO! They're both dead! Yuck!".
	 * <p>2) When one of them was dead, tell the alive one that the other was dead.
	 * <p>3) When they were both alive, compare their sums of strength.
	 * <li>If the sums of strength are the same, 
	 * claim they are both dead and the warriors in their army are dead.
	 * <li>The one who has more strength wins the battle, 
	 * the loser noble is dead and the warriors in his army are dead, too.
	 * <li>The warriors in winner noble army reduce their strengths by a ratio.
	 * <li>The ratio is the sum strength of the loser noble / the sum strength of the winner noble.
	 * For example, if the losing army had a combined strength that was 1/4 the size of the winning army's, 
	 * then each soldier in the winning army will have their own strength reduced by 1/4.
	 * @param enemy the enemy noble
	 */
	public void battle(Noble enemy){
		Noble enemyNoble = enemy;
		System.out.println(name + " battles " + enemyNoble.getNobleName());
		if(this.isDead() && enemyNoble.isDead()){
			System.out.println("Oh, NO! They're both dead! Yuck!");
		}
		else if(this.isDead() || enemyNoble.isDead()){
			if(this.isDead()){
				System.out.println("He's dead, " + enemyNoble.getNobleName());
			}
			else if(enemyNoble.isDead()){
				System.out.println("He's dead, " + name);
			}
		}
		else{
			if(this.calculateStrength() == enemyNoble.calculateStrength()){
				System.out.println("Mutual Annihilation: " + name + " and " 
			+ enemyNoble.getNobleName() + " die at each other's hands");
				this.setDead();
				for(int i = 0; i < army.size(); i++){
					army.get(i).setLoserStrength();
					army.get(i).setDead();
				}
				enemyNoble.setDead();
				for(int i = 0; i < enemyNoble.getArmy().size(); i++){
					enemyNoble.getArmy().get(i).setLoserStrength();
					enemyNoble.getArmy().get(i).setDead();
				}
			}
			else if(this.calculateStrength() > enemyNoble.calculateStrength()){
				System.out.println(name + " defeats " + enemyNoble.getNobleName());
				float ratio = 1;
				ratio = enemyNoble.calculateStrength() / this.calculateStrength();
				for(int i = 0; i < army.size(); i++){
					army.get(i).reduceStrength(ratio);
				}
				enemyNoble.setDead();
				for(int i = 0; i < enemyNoble.getArmy().size(); i++){
					enemyNoble.getArmy().get(i).setLoserStrength();
					enemyNoble.getArmy().get(i).setDead();
				}
			}
			else if(this.calculateStrength() < enemyNoble.calculateStrength()){
				System.out.println(enemyNoble.getNobleName() + " defeats " + name);
				float ratio = 1;
				ratio = this.calculateStrength() / enemyNoble.calculateStrength();
				for(int i = 0; i < enemyNoble.getArmy().size(); i++){
					enemyNoble.getArmy().get(i).reduceStrength(ratio);
				}
				this.setDead();
				for(int i = 0; i < army.size(); i++){
					army.get(i).setLoserStrength();
					army.get(i).setDead();
				}
			}
		}
	}
	/**
	 * Sets the isDead status.
	 */
	public void setDead(){
		if(!isDead){
			isDead = true;
		}
		else{
			System.out.println("already dead");
		}
	}
	/**
	 * Get the boolean status of isDead.
	 * @return isDead the status of noble
	 */
	public boolean isDead(){
		return isDead;
	}
	/**
	 * Calculates the sum strength of army.
	 * @return sumStrength the sum strength of the whole army of the noble
	 */
	public float calculateStrength(){
		float sumStrength = 0;
		for(int i = 0; i < army.size(); i++){
			sumStrength += army.get(i).getWarriorStrength();
		}
		return sumStrength;
	}
	/**
	 * Deletes the warrior in army and trim the size of army.
	 * @param warrior going to be deleted warrior
	 */
	public void deleteWarrior(Warrior warrior){
		army.remove(army.indexOf(warrior));
		army.trimToSize();
	}
	/**
	 * Gets the name of noble.
	 * @return name the name of noble
	 */
	public String getNobleName(){
		return name;
	}
	/**
	 * Gets the army of noble.
	 * @return army the army of the noble
	 */
	public ArrayList<Warrior> getArmy(){
		return army;
	}
	/**
	 * Prints out the army information of a noble, such as 
	 * <p>Lancelot du Lac has an army of 2
	 * <p>Conan: 0.0
	 * <p>Hercules: 0.0
	 */
	public String toString(){
		if(army.size() > 0){
			String armyInfo = new String();
			for(int i = 0; i < army.size(); i++){
				armyInfo = armyInfo + "\n    " + army.get(i).getWarriorName() + ": " 
			+ army.get(i).getWarriorStrength();
			}
			return name + " has an army of " + army.size() + armyInfo;
		}
		else{
			return name + " has an army of " + army.size() + "\n";
		}		
	}
}
/**
 * A <code>Warrior</code> object represents a warrior.
 * A warrior has a name, his strength, status of hire (hired or not), 
 * status of live (dead or not) and an noble owner (who hired this warrior).
 * A warrior can run away, when he has been hired by a noble.
 * A warrior can be hired by only one noble, cannot be hired again.
 * A warrior can reduce his strength by a ratio, when he lost a battle.
 * A warrior is dead, when his strength is 0.
 */
class Warrior{
	private String name;
	private float strength;
	private boolean isHired;
	private boolean isDead;
	private Noble owner;
	
	public Warrior(String name, float strength){
		this.name = name;
		this.strength = strength;
		isHired = false;
		isDead = false;
		owner = null;
	}
	/**
	 * Gets the boolean status of hire of the warrior.
	 * @return isHired the boolean status of hire
	 */
	public boolean isHired(){
		return isHired;
	}
	/**
	 * Gets the boolean status of dead of the warrior.
	 * @return isDead the boolean status of dead
	 */
	public boolean isDead(){
		return isDead;
	}
	/**
	 * Sets the isDead status to true.
	 */
	public void setDead(){
		if(!isDead){
			isDead = true;
		}	
	}
	/**
	 * Reduces the strength of warrior by a given ratio.
	 * @param ratio given by noble battle
	 */
	public void reduceStrength(float ratio){
		strength = (1 - ratio) * strength;
	}
	/**
	 * Sets the loser Strength to 0.
	 */
	public void setLoserStrength(){
		strength = 0;
	}
	/**
	 * Sets the owner of the warrior.
	 * @param owner the one who hired the warrior
	 */
	public void hiredBy(Noble owner){
		this.owner = owner;
		isHired = true;
	}
	/**
	 * Runs away from a noble army and the owner of this warrior will delete the warrior from army.
	 * A warrior can run away, when he is alive and he is hired by a noble.
	 */
	public void runaway(){
		if(!isDead && isHired){
			owner.deleteWarrior(this);
			System.out.println("So long " + owner.getNobleName() + ". I'm out'a here! -- " + name);
		}
		else{
			System.out.println(name + " is dead or haven't been hired and cannot runaway. ");
		}
	}
	/**
	 * Gets the warrior name.
	 * @return name the warrior name
	 */
	public String getWarriorName(){
		return name;
	}
	/**
	 * Gets the strength of warrior.
	 * @return strength the strength of warrior
	 */
	public float getWarriorStrength(){
		return strength;
	}
}