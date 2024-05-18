package Business.Entities;

import Business.Entities.Generator.BasicGenerator;
import Business.Entities.Generator.Generator;
import Business.Entities.Generator.HighGenerator;
import Business.Entities.Generator.MidGenerator;
import Business.Entities.Improvement.BasicImprovement;
import Business.Entities.Improvement.HighImprovement;
import Business.Entities.Improvement.Improvement;
import Business.Entities.Improvement.MidImprovement;
import Business.Exception.GeneratorException.NotEnoughCurrencyException;

import java.util.ArrayList;
import java.util.List;

/*Class to represent store where the user will be able to buy the improvements for the Generators*/
public class ImprovementStore {
    Game game;
    List<Improvement> improvements;
}
