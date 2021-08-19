import React, { Component } from 'react';
import EditableCell from "./EditableCell";

class ProductRowMeds extends Component{
    constructor(props) {
        super(props);
    }
    onDelEvent() {
        this.props.onDelEvent(this.props.product);

    }
    onUpdateEvent() {
        this.props.onUpdateEvent(this.props.product);
    }
    render() {
        return (
            <tr className="eachRow">
                <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
                    type: "name",
                    value: this.props.product.name,
                    id: this.props.product.id
                }}/>
                <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
                    type: "sideEfects",
                    value: this.props.product.sideEfects,
                    id: this.props.product.id
                }}/>
                <EditableCell onProductTableUpdate={this.props.onProductTableUpdate} cellData={{
                    type: "dosage",
                    value: this.props.product.dosage,
                    id: this.props.product.id
                }}/>
                <td className="del-cell">
                    <input type="button" onClick={this.onDelEvent.bind(this)} value="X" className="del-btn"/>
                </td>
                <td className="add-cell">
                    <input type="button" onClick={this.onUpdateEvent.bind(this)} value="+" className="add-btn"/>
                </td>
            </tr>
        );
    }
}

export default ProductRowMeds;