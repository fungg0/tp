package seedu.duke.command;

public class DesiredGpaCommand extends Command{
    public final float desiredGPA;

    public DesiredGpaCommand(float desiredGPA) {
        this.desiredGPA = desiredGPA;
    }

    @Override
    public void execute(String userInput) {
        System.out.println(desiredGPA);
    }
}
