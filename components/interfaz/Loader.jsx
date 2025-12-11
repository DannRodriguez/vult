import React, { Component } from 'react';
import {Modal} from "antd";
import logo from '../../img/loader.gif';
import BlockUI from 'react-block-ui';

class BlockLoader extends Component {
	componentDidMount() {
		if (this.props.id && this.props.bloquearTodo) {
			window.addEventListener('resize', this.redimension);
		}
	}

	componentDidUpdate() {
		if (this.props.id && this.props.bloquearTodo && !this.haBloqueado) {
			this.haBloqueado = true;
			this.redimension();
		}
	}

	componentWillUnmount() {
		if (this.props.id && this.props.bloquearTodo) {
			window.removeEventListener('resize', this.redimension);
		}
	}

	redimension = () => {
		const divContent = document.getElementById(this.props.id);
		divContent.style.height = '0px';

		setTimeout(() => {
			const height = document.documentElement.scrollHeight;
			divContent.style.height = height + 'px';
		}, 0);
	}

	render() {
		return (
			<BlockUI id={this.props.id}
					tag='div'
			        blocking={this.props.blocking}
					loader={<img src={logo} style={{width: '200px'}}/>}
					className={this.props.className}>
				{this.props.children}
			</BlockUI>
		);
	}
}

export function Loader ({blocking}){
    return(
        <Modal 	title={null} 
				className="modal-loader" 
				open={blocking} 
				closable={false}
                centered footer={null} 
				maskClosable={false}
				transitionName=''>
                    
					<div className ="divCenterUtil">
                        <img src={logo} style={{width: '200px'}}/>
                    </div>
        </Modal>
    );
}

export default BlockLoader;