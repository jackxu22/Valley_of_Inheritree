/**
 * The main class to setup and run the game.
 *
 * @author Adrian Kristanto
 */
package game;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.Player;
import game.actors.creatures.GoldenBeetle;
import game.actors.creatures.OmenSheep;
import game.actors.creatures.SpiritGoat;
import game.actors.creatures.boss.BedOfChaos;
import game.actors.npc.NpcGuts;
import game.actors.npc.NpcKale;
import game.actors.npc.NpcSellen;
import game.behaviours.behaviourselectors.BehaviourSelector;
import game.behaviours.behaviourselectors.PriorityBehaviourSelector;
import game.behaviours.behaviourselectors.RandomBehaviourSelector;
import game.fishing.Pond;
import game.grounds.Blight;
import game.grounds.Floor;
import game.grounds.Soil;
import game.grounds.Wall;
import game.healing.items.Talisman;
import game.plants.BloodroseSeed;
import game.plants.InheritreeSeed;
import game.teleport.TeleportationGate;
import game.ui.FancyMessage;
import java.util.Arrays;
import java.util.List;

public class Application {

    /**
     * Main method to run the game application. Sets up the game world, map, player, NPCs, and
     * items.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {

        World world = new World(new Display());

        FancyGroundFactory groundFactory = new FancyGroundFactory(new Blight(), new Wall(),
                new Floor(), new Soil());

        List<String> map = Arrays.asList("xxxx...xxxxxxxxxxxxxxxxxxxxxxx........xx",
                "xxx.....xxxxxxx..xxxxxxxxxxxxx.........x",
                "..........xxxx....xxxxxxxxxxxxxx.......x",
                "....xxx...........xxxxxxxxxxxxxxx.....xx",
                "...xxxxx...........xxxxxxxxxxxxxx.....xx",
                "...xxxxxxxxxx.......xxxxxxxx...xx......x",
                "....xxxxxxxxxx........xxxxxx...xxx......",
                "....xxxxxxxxxxx.........xxx....xxxx.....",
                "....xxxxxxxxxxx................xxxx.....",
                "...xxxx...xxxxxx.....#####.....xxx......",
                "...xxx....xxxxxxx....#___#.....xx.......",
                "..xxxx...xxxxxxxxx...#___#....xx........",
                "xxxxx...xxxxxxxxxx...##_##...xxx.......x",
                "xxxxx..xxxxxxxxxxx.........xxxxx......xx",
                "xxxxx..xxxxxxxxxxxx.......xxxxxx......xx");

        GameMap gameMap = new GameMap("Valley of the Inheritree", groundFactory, map);
        world.addGameMap(gameMap); //

        // Create and add a second map - Limveld
        List<String> limveldMap = Arrays.asList(
                ".............xxxx",
                "..............xxx",
                "................x",
                ".................",
                "................x",
                "...............xx",
                "..............xxx",
                "..............xxx",
                "..............xxx",
                ".............xxxx",
                ".............xxxx",
                "....xxx.....xxxxx",
                "....xxxx...xxxxxx"
        );
        GameMap limveldGameMap = new GameMap("Limveld", groundFactory, limveldMap);
        world.addGameMap(limveldGameMap); // Add to the same world

        // BEHOLD, ELDEN THING!
        for (String line : FancyMessage.TITLE.split("\n")) {
            new Display().println(line); //
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        //Create TeleportationGate
        TeleportationGate gateInValley = new TeleportationGate();
        TeleportationGate gateInLimveld = new TeleportationGate();

        // Set Teleportation destination
        gateInValley.addDestination(limveldGameMap.at(8, 6));
        gateInLimveld.addDestination(gameMap.at(23, 14));

        // Place the TeleportationGate on the map
        gameMap.at(23, 14).setGround(gateInValley);
        limveldGameMap.at(8, 6).setGround(gateInLimveld);

        gameMap.at(22, 13).setGround(new Pond());

        Player player = new Player("Farmer", '@', 100);
        world.addPlayer(player, gameMap.at(23, 11));

        player.addItemToInventory(new BloodroseSeed());
        player.addItemToInventory(new InheritreeSeed());

        // Create BehaviourSelector
        BehaviourSelector prioritySelector = new PriorityBehaviourSelector();
        BehaviourSelector randomSelector = new RandomBehaviourSelector();

        // Create instances of creatures using different BehaviourSelector
        // Creatures with prioritySelector
        SpiritGoat spiritGoatPriority = new SpiritGoat(prioritySelector);
        OmenSheep omenSheepPriority = new OmenSheep(prioritySelector);
        GoldenBeetle goldenBeetlePriority = new GoldenBeetle(prioritySelector);

        // Creatures with randomSelector
        SpiritGoat spiritGoatRandom = new SpiritGoat(randomSelector);
        OmenSheep omenSheepRandom = new OmenSheep(randomSelector);
        GoldenBeetle goldenBeetleRandom = new GoldenBeetle(randomSelector);

        // Place creatures on the map
        gameMap.addActor(spiritGoatPriority, gameMap.at(20, 10));
        gameMap.addActor(omenSheepPriority, gameMap.at(20, 6));
        gameMap.addActor(goldenBeetlePriority, gameMap.at(18, 10));

        limveldGameMap.addActor(spiritGoatRandom, limveldGameMap.at(5, 5));
        limveldGameMap.addActor(omenSheepRandom, limveldGameMap.at(6, 5));
        limveldGameMap.addActor(goldenBeetleRandom, limveldGameMap.at(7, 5));

        // Initialize the NPCs
        NpcSellen NPCSellen = new NpcSellen();
        NpcKale NPCKale = new NpcKale();
        NpcGuts NPCGuts = new NpcGuts();

        // game setup

        gameMap.addActor(NPCSellen, gameMap.at(10, 5));
        gameMap.addActor(NPCKale, gameMap.at(35, 12));
        gameMap.addActor(NPCGuts, gameMap.at(5, 13));

        BedOfChaos bedOfChaos = new BedOfChaos();
        limveldGameMap.addActor(bedOfChaos, limveldGameMap.at(9,6 ));

        gameMap.at(24, 11).addItem(new Talisman()); //
        world.run();
    }
}