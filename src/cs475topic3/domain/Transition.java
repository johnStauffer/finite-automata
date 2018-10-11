package cs475topic3.domain;

public class Transition
{
   private String fromState;
   private char label;
   private String toState;

   public String getFromState()
   {
      return fromState;
   }

   public void setFromState(String fromState)
   {
      this.fromState = fromState;
   }

   public char getLabel()
   {
      return label;
   }

   public void setLabel(char label)
   {
      this.label = label;
   }

   public String getToState()
   {
      return toState;
   }

   public void setToState(String toState)
   {
      this.toState = toState;
   }

}
