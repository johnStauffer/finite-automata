package cs475topic3.domain;

import java.util.List;

public class FiniteAutomata
{
   private String startState;
   private List<String> acceptStates;
   private List<String> states;


   public String getStartState()
   {
      return startState;
   }

   public void setStartState(String startState)
   {
      this.startState = startState;
   }

   public List<String> getAcceptStates()
   {
      return acceptStates;
   }

   public void setAcceptStates(List<String> acceptStates)
   {
      this.acceptStates = acceptStates;
   }

   public List<String> getStates()
   {
      return states;
   }

   public void setStates(List<String> states)
   {
      this.states = states;
   }
}
