import React from "react";
import '../styles/questionStyle.css';
import '../styles/answerStyle.css';
import { Icon } from '@iconify/react';
import chevronUp from '@iconify/react/octicon/chevron-up';
import chevronDown from '@iconify/react/octicon/chevron-down';
import trashcan from '@iconify/react/octicon/trashcan';
import pencil from '@iconify/react/octicon/pencil';


const QuestionContentView = ({ currentQuestion, currentUser, handleVoteQuestion, setQuestionModal, onDeleteQuestion, banUser, unbanUser }) => (
    <div className="row">
        <div className="question-vote column">
            <div>
                <button className="score disabled">Votes<br />{currentQuestion.votes}</button>
                <br />
                {currentUser.username !== currentQuestion.username &&
                    <button className="icon-button" onClick={() => handleVoteQuestion(currentQuestion, currentUser, "UP")}> <Icon className="icon" icon={chevronUp} /></button>}
                <br />
                {currentUser.username !== currentQuestion.username &&
                    <button className="icon-button" onClick={() => handleVoteQuestion(currentQuestion, currentUser, "DOWN")}> <Icon className="icon" icon={chevronDown} /></button>}
            </div>
        </div>
        <div className="card question-content fixed">
            {currentQuestion.date}
            <div className="card-body ">
                <div className="form-group green-border-focus">
                    <label form="exampleFormControlTextarea5"></label>
                    <textarea className="form-control" id="exampleFormControlTextarea5" rows="9"
                        value={currentQuestion.text} disabled></textarea>
                </div>
            </div>
        </div >
        <div className="question-options column">
            <button className="score dropdown-toggle" type="button" data-toggle="collapse" data-target="#dropdown" aria-expanded="false" aria-controls="collapseExample">
                User<br />{currentQuestion.username}</button>
            <div className="collapse" id="dropdown">
                <button className="dropdown-item disabled">Points: {currentQuestion.userPoints}</button>
                {(currentUser.permission === "ADMIN" && currentQuestion.userStatus === "ALLOWED") &&
                    <button type="button" className="btn btn-danger" onClick={() => banUser(currentQuestion.authorId, "BANNED")}>Ban</button>}
                {(currentUser.permission === "ADMIN" && currentQuestion.userStatus === "BANNED") &&
                    <button type="button" className="btn btn-success" onClick={() => banUser(currentQuestion.authorId, "ALLOWED")}>Unban</button>}
            </div>

            <div className="score-divider" />
            {currentUser.permission === "ADMIN" &&
                <button className="icon-button" type="button" data-toggle="modal" data-target="#editQuestionModal"
                    onClick={() => setQuestionModal(currentQuestion)}> <Icon className="icon-edit-delete" icon={pencil} /></button>}
            <br />
            <br />
            {currentUser.permission === "ADMIN" &&
                <button className="icon-button" onClick={() => onDeleteQuestion(currentQuestion)}> <Icon className="icon-edit-delete" icon={trashcan} /></button>}
        </div>
    </div>
);

export default QuestionContentView;