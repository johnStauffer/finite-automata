package cs475topic3.service;

import cs475topic3.domain.FiniteAutomata;
import cs475topic3.domain.Transition;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class DFARunnerService
{

   public String runFiniteAutomata(String inputString, FiniteAutomata finiteAutomata, Map<String, Transition> transitionMap)
         throws IllegalArgumentException
   {
      String state = finiteAutomata.getStartState();

      for(int i = 0; i < inputString.length(); i++)
      {
         char label = inputString.charAt(i);
         String transitionKey = buildTransitionKey(state, label);
         // if the key is not contained in the map, it means part of the user input is not inluded within the
         // alphabet supplied in the finite automata
         if(!transitionMap.containsKey(transitionKey)) throw new IllegalArgumentException();
         state = transitionMap.get(transitionKey).getToState();
      }

      return state;
   }

   public boolean isInAcceptState(List<String> acceptStates, String state)
   {
      return acceptStates.stream().anyMatch(s -> s.equals(state));
   }


   private String buildTransitionKey(String fromState, char label)
   {
      return fromState + String.valueOf(label);
   }

   public void showResults(boolean isAcceptState, List<Character> alphabet)
   {
      String message = String.format("Finished in accept state = %b \n Alphabet = %s", isAcceptState, alphabet.toString());
      JOptionPane.showMessageDialog(null, message);
   }

   public void showErrorMsg()
   {
      JOptionPane.showMessageDialog(null, "Invalid input string. All characters must be contained within DFA Alphabet");
   }

}
