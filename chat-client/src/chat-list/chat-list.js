import React, { Component } from 'react';
import './chat-list.css'
import axios from 'axios';


class chatList extends Component {

    constructor() {
        super();
        this.state = {
            namesList: []
        };

        // axios.post("http://localhost:8080/api/users").then(res => {
        //     this.setState({namesList: res.data});
        // })
    }

    render() {
        return (
            <div className="list-box">

                <div className="list-box-head">{this.props.title}</div>

                <div className="list-box-body">
                    <div >
                        {this.props.namesList.map((user, index) => {
                            return <div key={index} className="list-box-section"><a>{user.name}</a></div>
                        })}
                    </div>
                </div>

            </div>
        );
    }
}

export default chatList