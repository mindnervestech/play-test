package viewModel;

public class QuestionSetVM {

	public Long id;
	
	public int counter;
	public String marks;
	public String question;
	public String option1;
	public String option2;
	public String option3;
	public String option4;
	
	public boolean questionType;
	
	public QuestionSetVM(int counter,String id, String question, String option1,
			String option2, String option3, String option4, String questionType, String marks) {
		
		this.id = Long.parseLong(id);
		this.counter = counter;
		this.question = question;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.marks = marks;
		if(questionType.equals("obj")) {
			this.questionType = true;
		} else {
			this.questionType = false;
		}
	}

	
}
