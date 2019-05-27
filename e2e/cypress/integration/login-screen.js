describe("The Stack Overflow", function () {

  it("Filter questions", function () {
   
    loginUser();

    cy.get('[data-cy="searchTags"]').type("tag1 ");
    cy.get('[data-cy="goTags"]').click();

    cy.get('[data-cy="searchTitle"]').type("Question");
    cy.get('[data-cy="goTitle"]').click();
  });

  it("Add, edit and remove question", function () {

    loginUser();
     
    cy.get('[data-cy="createQuestion"]').click();
    cy.get('[data-cy="questionTitle"]').type("A Question #2");
    cy.get('[data-cy="questionTags"]').type("sort");
    cy.get('[data-cy="questionText"]').type("Text for A Question #2");
    cy.get('[data-cy="addQuestion"]').click();

    cy.get('[data-cy="questions"]').should("have.length", 16);
    
    cy.get('[data-cy="clickQuestion"]').first().click();
    cy.get('[data-cy="editQuestion"]').click();
    cy.get('[data-cy="editQuestionText"]').type(" Some edited text for this question");
    cy.get('[data-cy="onEditQuestion"]').click();
    cy.get('[data-cy="deleteQuestion"]').click();

    cy.get('[data-cy="questions"]').should("have.length", 15);

  });

  it("Add, edit and remove answers", function () {
   
    loginUser();
    
    cy.get('[data-cy="clickQuestion"]').first().click();
    cy.get('[data-cy="createAnswer"]').click();
    cy.get('[data-cy="answerText"]').type(" Some text for this answer");
    cy.get('[data-cy="addAnswer"]').click();

    cy.get('[data-cy="answers"]').should("have.length", 1);

    cy.get('[data-cy="editAnswer"]').click();
    cy.get('[data-cy="editAnswerText"]').type(" Some edited text for this answer");
    cy.get('[data-cy="onEditAnswer"]').click();
    cy.get('[data-cy="deleteAnswer"]').click();

    cy.get('[data-cy="answers"]').should("have.length", 0);
  });

  function loginUser(){
    
    cy.visit("/#/");

    cy.get('[data-cy="username"]').type("flyforfun98");
    cy.get('[data-cy="password"]').type("flavius1");
    cy.get('[data-cy="loginUser"]').click();
  }

})
