package seedu.duke.command;


import seedu.duke.exceptions.GpaNullException;

public class ViewGpaCommand extends Command{
    @Override
    public void execute(String userInput) {
        try {
            moduleList.tallyGPA();
            String formattedGPA = String.format("%.02f", moduleList.getCurrentGPA());
            System.out.println("Your current GPA is: " + formattedGPA);
        } catch (GpaNullException e) {
            System.out.println(e.getMessage());
        }
    }
}
