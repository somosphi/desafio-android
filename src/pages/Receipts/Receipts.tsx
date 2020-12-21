import React, {useState, useEffect, useCallback, useRef} from 'react';
import {useNavigation, useRoute} from '@react-navigation/native';
import ViewShot from 'react-native-view-shot';
import Share from 'react-native-share';
import Icon from 'react-native-vector-icons/Entypo';
import api from '../../services/Api';
import {formatDate, formatValue} from '../../helpers/helpers';
const RNFS = require('react-native-fs');

import {
  StyledSafeAreaView,
  ReceiptViewBorder,
  HeaderTextView,
  IconView,
  ReceiptText,
  ReceiptBodyView,
  ReceiptBodyTopText,
  ReceiptBodyBottomText,
  ShareButton,
  ShareButtonText,
} from './styles';

interface RouteParams {
  receiptId: string;
}

interface Receipt {
  authentication: string;
  createdAt: string;
  to: string;
  amount: number;
  description: string;
  tType: string;
  bankName?: string;
  from?: string;
}

const exportBackground = {backgroundColor: '#fff'};

const Receipts: React.FC = () => {
  const route = useRoute();
  const {receiptId} = route.params as RouteParams;
  const [receipt, setReceipt] = useState<Receipt>({} as Receipt);
  const viewShot = useRef<ViewShot>();

  const {goBack} = useNavigation();

  useEffect(() => {
    api.get(`/myStatement/detail/${receiptId}`).then((response) => {
      setReceipt({...response.data});
    });
  }, [receiptId]);

  const {
    amount,
    authentication,
    bankName,
    createdAt,
    description,
    to,
    from,
  } = receipt;

  const receiptArr = {
    'Tipo de movimentação': description,
    Valor: amount ? `R$ ${formatValue(amount)}` : '',
    Recebedor: to || from,
    'Instituição bancária': bankName ? bankName : 'Outra além do banco Phi',
    'Data/Hora': createdAt ? formatDate(createdAt) : '',
    Autenticação: authentication,
  };

  const navigateBack = useCallback(() => {
    goBack();
  }, [goBack]);

  const handleCaptureAndShare = async () => {
    if (!viewShot.current || !viewShot.current.capture) {
      return console.log('Error in viewShot.');
    } else {
      viewShot.current.capture().then((uri: string) => {
        RNFS.readFile(uri, 'base64').then((res: string) => {
          let urlString = 'data:image/jpeg;base64,' + res;
          let options = {
            title: 'Comprovante Banco Phi',
            message:
              'Olá! Você acaba de receber um compartilhamento de comprovante do Banco Phi. Olha só:',
            url: urlString,
            type: 'image/jpeg',
          };
          Share.open(options).catch((err) => {
            err && console.log(err);
          });
        });
      });
    }
  };

  return (
    <StyledSafeAreaView>
      <IconView onPress={navigateBack}>
        <Icon name="chevron-thin-left" size={25} color="#828282" />
      </IconView>
      <ViewShot
        ref={viewShot}
        style={exportBackground}
        options={{format: 'jpg', quality: 1}}>
        <HeaderTextView>
          <ReceiptText>Comprovante</ReceiptText>
        </HeaderTextView>
        <ReceiptViewBorder />
        {receipt &&
          Object.entries(receiptArr).map(function ([key, value], index) {
            return (
              <ReceiptBodyView key={index}>
                <ReceiptBodyTopText>{key}</ReceiptBodyTopText>
                <ReceiptBodyBottomText>
                  {value ? value : 'Carregando...'}
                </ReceiptBodyBottomText>
              </ReceiptBodyView>
            );
          })}
      </ViewShot>
      <ShareButton>
        <ShareButtonText onPress={handleCaptureAndShare}>
          Compartilhar
        </ShareButtonText>
      </ShareButton>
    </StyledSafeAreaView>
  );
};

export default Receipts;
