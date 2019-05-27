import React from "react";
import '../styles/questionStyle.css';
import '../styles/answerStyle.css';


const CreateAnswerView = ({ text, onCreate, onChange }) => (

    <div id="createAnswerModal" className="modal fade" role="dialog">
        <div className="modal-dialog">
            <div className="modal-content" >
                <div className="modal-header">
                    <h4 className="col-12 modal-title text-center">Add an Answer!</h4>
                </div>
                <div className="modal-body">
                    <br />
                    <br />
                    <div className="form-group green-border-focus">
                        <label form="exampleFormControlTextarea5">Text</label>
                        <textarea className="form-control" id="exampleFormControlTextarea5" rows="7"
                            value={text} onChange={e => onChange("text", e.target.value)} data-cy="answerText"></textarea>
                    </div>

                </div>
                <div className="modal-footer">
                    <button type="button" className="btn btn-primary" onClick={onCreate} data-dismiss="modal" data-cy="addAnswer">Create</button>
                    <div className="divider" />
                    <button type="button" className="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>
    </div>


);

export default CreateAnswerView;