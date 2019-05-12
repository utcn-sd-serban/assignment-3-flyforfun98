import React from "react";
import '../styles/questionStyle.css';
import '../styles/answerStyle.css';
import { Icon } from '@iconify/react';
import chevronUp from '@iconify/react/octicon/chevron-up';
import chevronDown from '@iconify/react/octicon/chevron-down';
import trashcan from '@iconify/react/octicon/trashcan';
import pencil from '@iconify/react/octicon/pencil';



const TableAnswerView = ({ answersCopy, currentUser, currentQuestion, onDelete, setAnswerModal, handleVoteAnswer, unbanUser, banUser }) => (

    <table id="foo" className="table table-hover">
        <tbody>
            {
                answersCopy.map((answer, index) => (
                    <tr className="table-primary" key={index}>
                        <td>
                            <div>
                                <button className="score disabled">Votes<br />{answer.votes}</button>
                                <br />
                                {answer.date}
                                <br />
                                {currentUser.username !== answer.username &&
                                    <button className="icon-button" onClick={() => handleVoteAnswer(currentUser, answer, currentQuestion, "UP")}> <Icon className="icon" icon={chevronUp} /></button>
                                }
                                <br />
                                {currentUser.username !== answer.username &&
                                    <button className="icon-button" onClick={() => handleVoteAnswer(currentUser, answer, currentQuestion, "DOWN")}> <Icon className="icon" icon={chevronDown} /></button>
                                }
                            </div>
                        </td>
                        <td className="text-column">
                            <div className="form-group green-border-focus">
                                <label form="exampleFormControlTextarea5"></label>
                                <textarea className="form-control" id="answerFormControlTextarea5" rows="7"
                                    value={answer.text} disabled> </textarea>
                            </div>
                        </td>

                        <td><button className="score dropdown-toggle" type="button" data-toggle="collapse" data-target="#dropdown" aria-expanded="false" aria-controls="collapseExample">
                            User<br />{answer.username}</button>
                            <div className="collapse" id="dropdown">
                                <button className="dropdown-item disabled">Points: {answer.userPoints}</button>
                                {(currentUser.permission === "ADMIN" && answer.userStatus === "ALLOWED") &&
                                    <button type="button" className="btn btn-danger" onClick={() => banUser(answer.authorId)} >Ban</button>}
                                {(currentUser.permission === "ADMIN" && answer.userStatus === "BANNED") &&
                                    <button type="button" className="btn btn-success" onClick={() => unbanUser(answer.authorId)}>Unban</button>}
                            </div>

                            <div className="score-divider" />

                            {(currentUser.permission === "ADMIN" || currentUser.username === answer.username) &&
                                <button className="icon-button" type="button" data-toggle="modal" data-target="#editAnswerModal"
                                    onClick={() => setAnswerModal(answer)} >

                                    <Icon className="icon-edit-delete" icon={pencil} />
                                    <br /></button>
                            }
                            <br />
                            <br />
                            {(currentUser.permission === "ADMIN" || currentUser.username === answer.username) &&
                                <button className="icon-button" type="button" onClick={() => onDelete(answer)}> <Icon className="icon-edit-delete" icon={trashcan} /></button>
                            }

                        </td>
                    </tr>))
            }
        </tbody>
    </table>

);

export default TableAnswerView;