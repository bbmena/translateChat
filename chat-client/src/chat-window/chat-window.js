import React, { Component } from 'react';

import * as ReactDOM from 'react-dom';

import './chat-window.css'
import Grid from '@instructure/ui-layout/lib/components/Grid'
import GridRow from '@instructure/ui-layout/lib/components/Grid/GridRow/index'
import GridCol from '@instructure/ui-layout/lib/components/Grid/GridCol/index'
import TextInput from '@instructure/ui-forms/lib/components/TextInput'

class chatWindow extends Component {

    sendMessage(){
        this.props.stompClient.send("/app/chat.sendMessage", {},
            JSON.stringify({lang:this.props.language, message: this.state.chatText, sender: this.props.sender}));
        this.setState({chatText:''});
    }

    handleMessage(messagePackage) {
        let message = messagePackage['allMessages'][this.props.language];
        message.sender = messagePackage.sender;
        this.setState({messages: [...this.state.messages, message]});
    }

    chatTextChange(event) {
        this.setState({chatText: event.target.value})
    }

    handleKeyPress(event){
        if(event.key === 'Enter'){
            this.sendMessage()
        }
    }

    scrollToBottom(){
        let { messageList } = this.refs;
        let scrollHeight = messageList.scrollHeight;
        let height = messageList.clientHeight;
        let maxScrollTop = scrollHeight - height;
        ReactDOM.findDOMNode(messageList).scrollTop = maxScrollTop > 0 ? maxScrollTop : 0;
    };

    componentDidUpdate() {
        this.scrollToBottom();
    }

    constructor(props) {
        super(props);
        this.state = {
            chatText: '',
            messages: []
        };
        this.props.stompClient.subscribe("/chat/public", (payload)=>{this.handleMessage(JSON.parse(payload.body))});
        this.sendMessage = this.sendMessage.bind(this);
        this.handleMessage = this.handleMessage.bind(this);
        this.chatTextChange = this.chatTextChange.bind(this);
        this.handleKeyPress = this.handleKeyPress.bind(this);
        this.scrollToBottom = this.scrollToBottom.bind(this);
    }

    render() {
        return (
            <div className="chat-container">
                <Grid rowSpacing="none" colSpacing="none">
                    <GridRow>
                        <GridCol>
                            <div ref="messageList" className="chat-text-area">
                                {this.state.messages.map((message, index) => {
                                    return (
                                        <div className="message-block" key={index}>
                                            <div className="message-sender">{message.sender}</div>
                                            <div className="message-body">{message.message}</div>
                                        </div>
                                    )
                                })}
                            </div>
                        </GridCol>
                    </GridRow>
                    <GridRow >
                        <GridCol>
                            <div className="text-entry">
                                <TextInput className="text-entry" onKeyPress={this.handleKeyPress} label="" placeholder="Type message and hit 'Enter'"
                                           value={this.state.chatText} onChange={this.chatTextChange}/>
                            </div>

                        </GridCol>
                    </GridRow>
                </Grid>
            </div>
        );
    }

}

export default chatWindow