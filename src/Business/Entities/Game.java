package Business.Entities;

import Business.Entities.Generator.BasicGenerator;
import Business.Entities.Generator.Generator;
import Business.Entities.Generator.HighGenerator;
import Business.Entities.Generator.MidGenerator;
import Business.Exception.BusinessException;
import Business.Exception.GeneratorException.GeneratorAddedException;

import java.util.ArrayList;

/**
 * Clase para guardar la información necesaria de cada juego que el usuario inicializa.
 */
public class Game {
    private int id_game; // Identificador del juego
    private float currency_count; // Cantidad de moneda del juego
    private boolean finished; // Indica si el juego ha finalizado
    private String mail_user; // Correo electrónico del usuario
    private final ArrayList<Generator> gameGenerators; // Lista de generadores del juego

    /**
     * Constructor de la clase Game.
     * Inicializa la lista de generadores.
     */
    public Game() {
        this.gameGenerators = new ArrayList<>();
    }

    /**
     * Constructor de la clase Game.
     * @param id_game Identificador del juego.
     * @param currency_count Cantidad de moneda del juego.
     * @param finished Indica si el juego ha finalizado.
     * @param mail_user Correo electrónico del usuario.
     */
    public Game(int id_game, float currency_count, boolean finished, String mail_user) {
        this.id_game = id_game;
        this.currency_count = currency_count;
        this.finished = finished;
        this.mail_user = mail_user;
        this.gameGenerators = new ArrayList<>();
    }

    /**
     * Obtiene el identificador del juego.
     * @return Identificador del juego.
     */
    public int getIdGame() {
        return id_game;
    }

    /**
     * Obtiene la cantidad de moneda del juego.
     * @return Cantidad de moneda del juego.
     */
    public float getCurrencyCount() {
        return currency_count;
    }

    /**
     * Resta una cantidad de moneda al juego.
     * @param amount Cantidad a restar.
     */
    public void substractCurrency(float amount) {
        currency_count -= amount;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return Correo electrónico del usuario.
     */
    public String getUser() {
        return mail_user;
    }

    /**
     * Comprueba si el juego ha finalizado.
     * @return Verdadero si el juego ha finalizado, falso en caso contrario.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Añade un generador al juego.
     * @param type Tipo de generador a añadir.
     * @param idGenerator Identificador del generador.
     * @param improvementId Identificador de la mejora aplicada al generador.
     */
    public void addGeneratorToGame(String type, int idGenerator, int improvementId) {
        Generator toAdd;
        for (Generator auxGen : gameGenerators) {
            if (auxGen.getClass().getSimpleName().contains(type)) {
                currency_count -= auxGen.getGeneratorPrice();
                auxGen.addGenerator();
                return;
            }
        }
        switch (type) {
            case "Basic":
                toAdd = new BasicGenerator(id_game);
                break;
            case "Mid":
                toAdd = new MidGenerator(id_game);
                break;
            case "High":
                toAdd = new HighGenerator(id_game);
                break;
            default:
                toAdd = new BasicGenerator(id_game);
                break;
        }
        toAdd.setIdGenerator(idGenerator);
        toAdd.setIdImprovement(improvementId);
        currency_count -= toAdd.getGeneratorPrice();
        gameGenerators.add(toAdd);
    }

    /**
     * Inicializa un generador en el juego.
     * @param generator Generador a inicializar.
     */
    public void initGenerator(Generator generator) {
        gameGenerators.add(generator);
    }

    /**
     * Obtiene si el juego ha finalizado.
     * @return Verdadero si el juego ha finalizado, falso en caso contrario.
     */
    public boolean getFinished() {
        return finished;
    }

    /**
     * Incrementa la cantidad de moneda del juego.
     */
    public void increaseCurrency() {
        currency_count += 100;
    }

    /**
     * Actualiza la cantidad de moneda del juego.
     * @param nCurrencies Nueva cantidad de moneda.
     */
    public void updateCurrency(int nCurrencies) {
        currency_count = nCurrencies;
    }

    /**
     * Realiza la producción de los generadores del juego.
     */
    public void generatorsProduction() {
        for (Generator aux : gameGenerators) {
            if(aux.getImprovement().getLevel() == 0) {
                currency_count += (aux.generateCurrency());
            } else {
                currency_count += (float) (aux.generateCurrency() * aux.getImprovement().getLevel() * aux.getImprovement().getMultiplier());
            }
        }
    }

    /**
     * Obtiene la lista de generadores del juego.
     * @return Lista de generadores del juego.
     */
    public ArrayList<Generator> getGameGenerators() {
        return gameGenerators;
    }

    /**
     * Establece el identificador del juego.
     * @param generatedId Identificador del juego.
     */
    public void setId_game(int generatedId) {
        this.id_game = generatedId;
    }


        /**
         * Establece si el juego ha finalizado.
         * @param finished Verdadero si el juego ha finalizado, falso en caso contrario.
         */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
